package fr.skyle.escapy.data.repository.auth.api

import fr.skyle.escapy.data.enums.AuthProvider

interface AuthRepository {

    fun isUserLoggedIn(): Boolean

    fun getAccountEmail(): String?

    fun getAccountAuthProvider(): AuthProvider

    suspend fun signUpAsGuest(): Result<Unit>

    suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit>

    suspend fun changeEmailForEmailPasswordProvider(
        newEmail: String,
        currentPassword: String,
    ): Result<Unit>

    suspend fun changePasswordForEmailPasswordProvider(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit>

    suspend fun signOut()
}