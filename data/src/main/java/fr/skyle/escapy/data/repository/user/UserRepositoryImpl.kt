package fr.skyle.escapy.data.repository.user

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.ext.awaitWithTimeout
import fr.skyle.escapy.data.ext.requireUser
import fr.skyle.escapy.data.ext.toUserPost
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
) : UserRepository {

    // Helpful methods

    override fun isUserLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    override fun getAuthProvider(): AuthProvider {
        val providers = firebaseAuth.currentUser?.providerData ?: emptyList()

        // Exclude the first element: it is ALWAYS `firebase`
        val realProvider = providers
            .map { it.providerId }
            .firstOrNull { it != "firebase" }

        return AuthProvider.fromProviderId(realProvider) ?: AuthProvider.ANONYMOUS
    }

    // Firebase

    private fun createUser(
        uid: String,
        userNamePrefix: String,
        email: String?,
    ): User =
        User(
            uid = uid,
            name = "${userNamePrefix}_${uid.takeLast(10)}",
            email = email,
            avatarType = Avatar.entries.random().type,
            createdAt = System.currentTimeMillis()
        )

    private suspend fun insertUserFirebase(
        uid: String,
        user: User
    ) {
        dbRef.child(FirebaseNode.Users.path)
            .child(uid)
            .setValue(user.toUserPost())
            .awaitWithTimeout()
    }

    override suspend fun signInAsGuest(): Result<Unit> {
        return try {
            val result = firebaseAuth.signInAnonymously().awaitWithTimeout()

            val user = result.requireUser()
            val currentUserUid = user.uid
            val currentUser = createUser(
                uid = currentUserUid,
                userNamePrefix = "Guest",
                email = user.email
            )

            // Insert in Firebase
            insertUserFirebase(
                uid = currentUserUid,
                user = currentUser
            )

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            userDao.insert(currentUser)

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

            val user = result.requireUser()
            val currentUserUid = user.uid
            val currentUser = createUser(
                uid = currentUserUid,
                userNamePrefix = "Player",
                email = user.email
            )

            // Insert in Firebase
            insertUserFirebase(
                uid = currentUserUid,
                user = currentUser
            )

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            userDao.insert(currentUser)

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun changePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        return try {
            // Create a AuthCredential
            val user = firebaseAuth.currentUser
            val email = user?.email ?: ""
            val credential = EmailAuthProvider.getCredential(email, currentPassword)

            // First reauthenticate to be sure that the user has logged recently
            user?.reauthenticate(credential)?.awaitWithTimeout()

            // Then update user password
            user?.updatePassword(newPassword)?.awaitWithTimeout()

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        FirebaseAuth.getInstance().signOut()
    }

    fun fetchCurrentUser(): Flow<User?> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.getValue(User::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            dbRef.child(FirebaseNode.Users.path)
                .child("currentUserId")
                .addValueEventListener(listener)

            awaitClose {
                dbRef.child(FirebaseNode.Users.path)
                    .child("currentUserId")
                    .removeEventListener(listener)
            }
        }

// Local

    override fun watchCurrentUser(): Flow<User?> =
        userDao.watchUser(firebaseAuth.currentUser?.uid ?: "").distinctUntilChanged()
}