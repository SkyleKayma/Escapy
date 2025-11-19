package fr.skyle.escapy.data.repository.user

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
import fr.skyle.escapy.data.ext.toUserPost
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
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
        val providerId = firebaseAuth.currentUser?.providerId

        return AuthProvider.fromProviderId(providerId) ?: AuthProvider.ANONYMOUS
    }

    // Firebase

    private suspend fun insertUserFirebase(
        uid: String,
        user: User
    ) {
        dbRef.child(FirebaseNode.Users.path)
            .child(uid)
            .setValue(user.toUserPost())
            .awaitWithTimeout()
    }

    override suspend fun signInAsGuest() {
        val result = firebaseAuth.signInAnonymously().awaitWithTimeout()

        result?.user?.let { user ->
            // To prevent screens to switch too fast
            delay(200)

            val currentUserUid = user.uid
            val currentUser = User(
                uid = currentUserUid,
                name = "Guest_${user.uid.takeLast(10)}",
                email = user.email,
                avatarType = Avatar.entries.random().type,
            )

            // Insert in Firebase
            insertUserFirebase(
                uid = currentUserUid,
                user = currentUser
            )

            // Insert in DB
            // This is helpful to have user info on home screen directly after login
            userDao.insert(currentUser)
        } ?: throw Exception("No user returned from API")
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