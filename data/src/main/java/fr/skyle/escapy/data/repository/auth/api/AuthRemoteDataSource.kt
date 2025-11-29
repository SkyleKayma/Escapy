package fr.skyle.escapy.data.repository.auth.api

interface AuthRemoteDataSource {

    suspend fun signUpAsGuest()

    suspend fun signUp(
        email: String,
        password: String
    )

    suspend fun signIn(
        email: String,
        password: String
    )

    fun signOut()

    suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String
    )

    suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    )

    suspend fun deleteAccountFromEmailProvider(
        currentPassword: String
    )

    suspend fun deleteAccountFromAnonymousProvider()
}