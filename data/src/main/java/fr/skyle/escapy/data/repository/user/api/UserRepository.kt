package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchUser(userId: String)

    suspend fun fetchCurrentUser()

    suspend fun updateRemoteAvatar(avatar: Avatar)

    suspend fun getCurrentUser(): User?

    fun watchUser(userId: String): Flow<User?>

    fun watchCurrentUser(): Flow<User?>
}