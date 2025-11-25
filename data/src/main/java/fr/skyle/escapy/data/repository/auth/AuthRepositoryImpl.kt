package fr.skyle.escapy.data.repository.auth

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.ext.awaitWithTimeout
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.requireUser
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import fr.skyle.escapy.data.vo.adapter.toUserRequestDTO
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

private const val PREFIX_GUEST = "Guest"
private const val PREFIX_PLAYER = "Player"

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
) : AuthRepository {

    override fun isUserLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    override fun getAccountEmail(): String? =
        firebaseAuth.currentUser?.email

    override fun getAccountAuthProvider(): AuthProvider {
        val providers = firebaseAuth.currentUser?.providerData ?: emptyList()

        // Exclude the first element: it is ALWAYS FirebaseAuthProvider.PROVIDER_ID
        val realProvider = providers
            .map { it.providerId }
            .firstOrNull { it != FirebaseAuthProvider.PROVIDER_ID }

        return AuthProvider.fromProviderId(realProvider) ?: AuthProvider.ANONYMOUS
    }

    override suspend fun signInAsGuest(): Result<Unit> {
        return try {
            val result = firebaseAuth.signInAnonymously().awaitWithTimeout()

            val firebaseUser = result.requireUser()
            val currentUserId = firebaseUser.uid
            val newUser = User.createDefault(
                userId = currentUserId,
                userNamePrefix = PREFIX_GUEST,
            )

            // Insert in Firebase
            dbRef.child(FirebaseNode.Users.path)
                .child(newUser.uid)
                .setValue(newUser.toUserRequestDTO())
                .awaitWithTimeout()

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            userDao.insert(newUser)

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
            val result =
                firebaseAuth.createUserWithEmailAndPassword(email, password).awaitWithTimeout()

            val firebaseUser = result.requireUser()
            val currentUserId = firebaseUser.uid
            val newUser = User.createDefault(
                userId = currentUserId,
                userNamePrefix = PREFIX_PLAYER,
            )

            // Insert in Firebase
            dbRef.child(FirebaseNode.Users.path)
                .child(newUser.uid)
                .setValue(newUser.toUserRequestDTO())
                .awaitWithTimeout()

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            userDao.insert(newUser)

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(email, password).awaitWithTimeout()

            val firebaseUser = result.requireUser()
            val userId = firebaseUser.uid

            // Read once from Realtime Database
            val response = dbRef
                .child(FirebaseNode.Users.path)
                .child(userId)
                .readOnce(UserRequestDTO::class.java)

            val currentUser = response.body

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            // TODO What should we do if user is null ?? Has been manually deleted ??
            currentUser?.toUser(userId)?.let { userDao.insert(it) }

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

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

            if (newEmail == user.email) {
                return Result.failure(Exception("New email is the same as the current one"))
            }

            // Create credentials
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

            // Create credentials
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

    override suspend fun signOut() {
        // Sign out from Firebase Auth
        FirebaseAuth.getInstance().signOut()

        // Remove all local data related to user
        userDao.deleteAll()
    }
}