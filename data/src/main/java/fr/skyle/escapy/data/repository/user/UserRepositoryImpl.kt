package fr.skyle.escapy.data.repository.user

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: DatabaseReference
) : UserRepository {

    override suspend fun insertUser(
        accessToken: String,
        name: String,
    ) {
        db.child(FirebaseNode.Users.path)
            .child(accessToken)
            .setValue(
                User(name = name)
            )
            .await()
    }

    override suspend fun getUser(): User? {
        val snapshot = db.child(FirebaseNode.Users.path)
            .child("currentUserId")
            .get()
            .await()

        return snapshot.getValue(User::class.java)
    }

    override fun watchUser(): Flow<User?> =
        callbackFlow {
            val listener = object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.getValue(User::class.java))
                }

                override fun onCancelled(error: DatabaseError) {
                    close(error.toException())
                }
            }

            db.child(FirebaseNode.Users.path)
                .child("currentUserId")
                .addValueEventListener(listener)

            awaitClose {
                db.child(FirebaseNode.Users.path)
                    .child("currentUserId")
                    .removeEventListener(listener)
            }
        }
}