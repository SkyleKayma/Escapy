package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserLocalDataSource {

    suspend fun insert(user: User)

    suspend fun getUser(userId: String): User?

    fun watchUser(userId: String): Flow<User?>
}