package fr.skyle.escapy.data.repository.auth.api

interface AuthRepository {

    suspend fun signUpAsGuest(): Result<Unit>

    suspend fun signUp(
        email: String,
        password: String,
    ): Result<Unit>

    suspend fun signIn(
        email: String,
        password: String,
    ): Result<Unit>

    fun signOut()

    suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String,
    ): Result<Unit>

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String,
    ): Result<Unit>

    suspend fun deleteAccountFromEmailProvider(
        currentPassword: String,
    ): Result<Unit>

    suspend fun deleteAccountFromAnonymousProvider(): Result<Unit>
}