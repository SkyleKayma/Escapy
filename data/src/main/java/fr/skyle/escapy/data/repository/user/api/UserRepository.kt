package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUser(userId: String): Result<Unit>

    suspend fun fetchCurrentUser(): Result<Unit>

    suspend fun updateRemoteAvatar(avatar: Avatar): Result<Unit>

    suspend fun getCurrentUser(): User?

    fun watchUser(userId: String): Flow<User?>

    fun watchCurrentUser(): Flow<User?>
}