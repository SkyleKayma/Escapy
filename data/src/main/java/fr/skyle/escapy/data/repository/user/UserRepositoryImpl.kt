package fr.skyle.escapy.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.db.dao.UserDao
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.updateOnce
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val firebaseAuth: FirebaseAuth,
    private val userDao: UserDao,
) : UserRepository {

    // Remote

    override suspend fun fetchCurrentUser(): Result<Unit> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.failure(Exception("There is no currentUser"))

        return fetchUser(userId)
    }

    override suspend fun fetchUser(userId: String): Result<Unit> {
        val response = dbRef
            .child(FirebaseNode.Users.path)
            .child(userId)
            .readOnce(UserRequestDTO::class.java)

        val user = response.body?.toUser(userId)

        return if (response.isSuccessful && user != null) {
            // Insert
            userDao.insert(user)

            Result.success(Unit)
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    override suspend fun updateAvatar(avatar: Avatar): Result<Unit> {
        val userId = firebaseAuth.currentUser?.uid
            ?: return Result.failure(Exception("There is no currentUser"))

        val update = mapOf(
            UserRequestDTO::avatarType.name to avatar.type
        )

        val response = dbRef
            .child(FirebaseNode.Users.path)
            .child(userId)
            .updateOnce(update)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(response.message()))
        }
    }

    // Local

    override fun watchCurrentUser(): Flow<User?> {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        return watchUser(userId)
    }

    override fun watchUser(userId: String): Flow<User?> =
        userDao.watchUser(userId).distinctUntilChanged()
}