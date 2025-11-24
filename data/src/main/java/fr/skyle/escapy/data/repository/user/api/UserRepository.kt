package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.vo.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun isUserLoggedIn(): Boolean

    fun getAuthProvider(): AuthProvider

    suspend fun signInAsGuest(): Result<Unit>

    suspend fun changePassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit>

    fun signOut()

    fun watchCurrentUser(): Flow<User?>

}