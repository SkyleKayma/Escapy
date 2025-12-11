package fr.skyle.escapy.data.repository.user

import com.google.firebase.auth.FirebaseAuth
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.repository.user.api.UserRemoteDataSource
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.data.vo.User
import fr.skyle.escapy.data.vo.mapper.toUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : UserRepository {

    override suspend fun fetchUser(userId: String) {
        val userRequestDTO = userRemoteDataSource.getUser(userId)
            ?: throw Exception("User not found")

        userLocalDataSource.insert(userRequestDTO.toUser(userId))
    }

    override suspend fun fetchCurrentUser() {
        val user = firebaseAuth.currentUser
            ?: throw Exception("No current user")

        return fetchUser(user.uid)
    }

    override suspend fun updateRemoteAvatar(avatar: Avatar) {
        val user = firebaseAuth.currentUser
            ?: throw Exception("No current user")

        userRemoteDataSource.updateAvatar(
            userId = user.uid,
            avatar = avatar
        )
    }

    override suspend fun getCurrentUser(): User? {
        val user = firebaseAuth.currentUser
            ?: throw Exception("No current user")

        return userLocalDataSource.getUser(user.uid)
    }

    override fun watchUser(userId: String): Flow<User?> =
        userLocalDataSource.watchUser(userId)

    override fun watchCurrentUser(): Flow<User?> {
        val userId = firebaseAuth.currentUser?.uid ?: ""
        return watchUser(userId)
    }
}