package fr.skyle.escapy.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.repository.user.api.UserRemoteDataSource
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun fetchUser(userId: String): Result<Unit> {
        val response = userRemoteDataSource.fetchUser(userId)

        val body = response.body

        return if (response.isSuccessful && body != null) {
            userLocalDataSource.insertUser(body.toUser(userId))

            Result.success(Unit)
        } else {
            Result.failure(Exception())
        }
    }

    override suspend fun fetchCurrentUser(): Result<Unit> {
        val user = firebaseAuth.currentUser ?: throw Exception("No current user")
        return fetchUser(user.uid)
    }

    override suspend fun updateAvatar(avatar: Avatar): Result<Unit> {
        val user = firebaseAuth.currentUser ?: throw Exception("No current user")

        val response = userRemoteDataSource.updateAvatar(user.uid, avatar)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(response.exception))
        }
    }

    override fun watchUser(userId: String): Flow<User?> =
        userLocalDataSource.watchUser(userId)

    override fun watchCurrentUser(): Flow<User?> {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        return watchUser(userId)
    }
}