package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun isUserLoggedIn() : Boolean

    fun getAuthProvider() : AuthProvider

    suspend fun signInAsGuest()

    fun watchCurrentUser(): Flow<User?>
}