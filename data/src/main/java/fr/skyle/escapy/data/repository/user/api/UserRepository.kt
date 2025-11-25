package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun fetchCurrentUser()

    suspend fun fetchUser(userId: String): Result<Unit>

    fun watchCurrentUser(): Flow<User?>

    fun watchUser(userId: String): Flow<User?>
}