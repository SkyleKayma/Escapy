package fr.skyle.escapy.data.repository.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.PREFIX_GUEST
import fr.skyle.escapy.data.PREFIX_PLAYER
import fr.skyle.escapy.data.db.ProjectDatabase
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.ext.awaitWithTimeout
import fr.skyle.escapy.data.repository.auth.api.AuthRemoteDataSource
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.ProjectDataStore
import fr.skyle.escapy.data.utils.getAuthProvider
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.requireUser
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import fr.skyle.escapy.data.vo.adapter.toUserRequestDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRemoteDataSourceImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val database: ProjectDatabase,
    private val dataStore: ProjectDataStore,
    private val dbRef: DatabaseReference,
    private val userDao: UserDao,
) : AuthRemoteDataSource {

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

    override suspend fun signUpAsGuest() {
        val authResult = firebaseAuth.signInAnonymously().awaitWithTimeout()
        postAuthOperations(
            authResult = authResult,
            isSignUp = true
        )
    }

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        val authResult =
            firebaseAuth.createUserWithEmailAndPassword(email, password).awaitWithTimeout()
        postAuthOperations(
            authResult = authResult,
            isSignUp = true
        )
    }

    override suspend fun signIn(
        email: String,
        password: String
    ) {
        val authResult =
            firebaseAuth.signInWithEmailAndPassword(email, password).awaitWithTimeout()
        postAuthOperations(
            authResult = authResult,
            isSignUp = false
        )
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    private fun FirebaseUser.reauthenticateForEmailProvider(
        currentEmail: String,
        currentPassword: String
    ): Task<Void> {
        val credential = EmailAuthProvider.getCredential(currentEmail, currentPassword)
        return reauthenticate(credential)
    }

    override suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String
    ) {
        val user = requireNotNull(firebaseAuth.currentUser)
        val currentEmail = requireNotNull(user.email)

        // Mandatory to get the most updated email
        user.reload().awaitWithTimeout()

        // Reauthenticate
        user.reauthenticateForEmailProvider(
            currentEmail = currentEmail,
            currentPassword = currentPassword
        ).awaitWithTimeout()

        // Send mail
        user.verifyBeforeUpdateEmail(newMail).awaitWithTimeout()
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ) {
        val user = requireNotNull(firebaseAuth.currentUser)
        val currentEmail = requireNotNull(user.email)

        // Mandatory to get the most updated email
        user.reload().awaitWithTimeout()

        // Reauthenticate
        user.reauthenticateForEmailProvider(
            currentEmail = currentEmail,
            currentPassword = currentPassword
        ).awaitWithTimeout()

        // Update password
        user.updatePassword(newPassword).awaitWithTimeout()
    }

    override suspend fun deleteAccountFromEmailProvider(currentPassword: String) {
        val user = requireNotNull(firebaseAuth.currentUser)
        val currentEmail = requireNotNull(user.email)

        // Mandatory to get the most updated email
        user.reload().awaitWithTimeout()

        // Reauthenticate
        user.reauthenticateForEmailProvider(
            currentEmail = currentEmail,
            currentPassword = currentPassword
        ).awaitWithTimeout()

        // Delete the account
        user.delete().awaitWithTimeout()
    }

    override suspend fun deleteAccountFromAnonymousProvider() {
        val user = requireNotNull(firebaseAuth.currentUser)

        // Delete the account
        user.delete().awaitWithTimeout()
    }

    override suspend fun linkAccountWithEmailProvider(
        email: String,
        password: String,
    ) {
        val user = requireNotNull(firebaseAuth.currentUser)
        val credential = EmailAuthProvider.getCredential(email, password)
        user.linkWithCredential(credential).awaitWithTimeout()
    }
}