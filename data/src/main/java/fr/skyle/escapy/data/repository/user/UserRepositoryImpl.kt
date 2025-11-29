package fr.skyle.escapy.data.repository.user

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.repository.user.api.UserRemoteDataSource
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.utils.FirebaseAuthHelper
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.adapter.toUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuthHelper: FirebaseAuthHelper,
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
        val userId = firebaseAuthHelper.requireCurrentUser().uid
        return fetchUser(userId)
    }

    override suspend fun updateAvatar(avatar: Avatar): Result<Unit> {
        val userId = firebaseAuthHelper.requireCurrentUser().uid

        val response = userRemoteDataSource.updateAvatar(userId, avatar)

        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(response.exception))
        }
    }

    override fun watchCurrentUser(): Flow<User?> {
        val userId = firebaseAuthHelper.getCurrentUser()?.uid ?: ""
        return watchUser(userId)
    }

    override fun watchUser(userId: String): Flow<User?> =
        userLocalDataSource.watchUser(userId)
}