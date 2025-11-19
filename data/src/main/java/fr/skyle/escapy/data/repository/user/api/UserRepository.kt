package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun signInAsGuest()

    fun watchCurrentUser(): Flow<User?>
}