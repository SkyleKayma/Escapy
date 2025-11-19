package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun isUserLoggedIn() : Boolean

    suspend fun signInAsGuest()

    fun watchCurrentUser(): Flow<User?>
}