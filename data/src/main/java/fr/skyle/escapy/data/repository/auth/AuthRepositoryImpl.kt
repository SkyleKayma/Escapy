package fr.skyle.escapy.data.repository.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.db.ProjectDatabase
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.ext.awaitWithTimeout
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.ProjectDataStore
import fr.skyle.escapy.data.utils.getAuthProvider
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.requireUser
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import fr.skyle.escapy.data.vo.adapter.toUserRequestDTO
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFIX_GUEST = "Guest"
private const val PREFIX_PLAYER = "Player"

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
    private val database: ProjectDatabase,
    private val dataStore: ProjectDataStore,
) : AuthRepository {

    // Helpful methods

    override fun isUserLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    override fun getAccountEmail(): String? =
        firebaseAuth.currentUser?.email

    override fun getAccountAuthProvider(): AuthProvider =
        firebaseAuth.currentUser?.getAuthProvider() ?: AuthProvider.ANONYMOUS

    // SignIn SignUp common methods

    private suspend fun handleUserSwitch(userId: String) {
        if (userId != dataStore.getCurrentUserId()) {
            withContext(Dispatchers.IO) {
                database.clearAllTables()
            }
            dataStore.setCurrentUserId(userId)
        }
    }

    private suspend fun postAuthOperations(
        authResult: AuthResult,
        isSignUp: Boolean
    ) {
        val firebaseUser = authResult.requireUser()
        val userId = firebaseUser.uid

        handleUserSwitch(userId)

        if (isSignUp) {
            val prefix = when (firebaseUser.getAuthProvider()) {
                AuthProvider.ANONYMOUS -> PREFIX_GUEST
                else -> PREFIX_PLAYER
            }

            val newUser = User.createDefault(userId, prefix)

            // Write
            dbRef.child(FirebaseNode.Users.path)
                .child(userId)
                .setValue(newUser.toUserRequestDTO())
                .awaitWithTimeout()
        }

        val response = dbRef
            .child(FirebaseNode.Users.path)
            .child(userId)
            .readOnce(UserRequestDTO::class.java)

        response.body
            ?.toUser(userId)
            ?.let {
                // This is helpful to have user info on home screen directly after login
                // TODO What should we do if user is null ?? Has been manually deleted ??
                userDao.insert(it)
            }
    }

    override suspend fun signUpAsGuest(): Result<Unit> {
        return try {
            val authResult = firebaseAuth.signInAnonymously().awaitWithTimeout()
            postAuthOperations(
                authResult = authResult,
                isSignUp = true
            )

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val authResult =
                firebaseAuth.createUserWithEmailAndPassword(email, password).awaitWithTimeout()
            postAuthOperations(
                authResult = authResult,
                isSignUp = true
            )

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // SignIn

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val authResult =
                firebaseAuth.signInWithEmailAndPassword(email, password).awaitWithTimeout()
            postAuthOperations(
                authResult = authResult,
                isSignUp = false
            )

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    // Update account

    override suspend fun changeEmailForEmailPasswordProvider(
        newEmail: String,
        currentPassword: String,
    ): Result<Unit> {
        return try {
            // Get User
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No user logged in"))

            // Get Email
            val email = user.email
                ?: return Result.failure(Exception("User has no email"))

            // Create credential
            val credential = EmailAuthProvider.getCredential(email, currentPassword)

            // First reauthenticate to be sure that the user has logged recently
            user.reauthenticate(credential).awaitWithTimeout()

            // Then send mail to update user mail
            user.verifyBeforeUpdateEmail(newEmail).awaitWithTimeout()

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePasswordForEmailPasswordProvider(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        return try {
            // Get User
            val user = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No user logged in"))

            // Get Email
            val email = user.email
                ?: return Result.failure(Exception("User has no email"))

            // Create credential
            val credential = EmailAuthProvider.getCredential(email, currentPassword)

            // First reauthenticate to be sure that the user has logged recently
            user.reauthenticate(credential).awaitWithTimeout()

            // Then update user password
            user.updatePassword(newPassword).awaitWithTimeout()

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}