package fr.skyle.escapy.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.ext.toUserPost
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
) : UserRepository {

    // Firebase

    override fun isUserLoggedIn(): Boolean =
        firebaseAuth.currentUser != null

    private suspend fun insertUser(
        uid: String,
        user: User
    ) {
        dbRef.child(FirebaseNode.Users.path)
            .child(uid)
            .setValue(user.toUserPost())
            .await()
    }

    override suspend fun signInAsGuest() {
        val result = firebaseAuth.signInAnonymously().await()

        result?.user?.let { user ->
            // To prevent screens to switch too fast
            delay(200)

            val currentUserUid = user.uid
            val currentUser = User(
                uid = currentUserUid,
                name = "Guest_${user.uid.takeLast(10)}",
                email = user.email
            )

            // Insert in Firebase
            insertUser(
                uid = currentUserUid,
                user = currentUser
            )

            // Insert in DB
            userDao.insert(currentUser)
        } ?: throw Exception("No user returned from API")
    }

    fun watchCurrentUserFirebase(): Flow<User?> =
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

    // Local DB

    override fun watchCurrentUser(): Flow<User?> =
        userDao.watchUser(firebaseAuth.currentUser?.uid ?: "").distinctUntilChanged()
}