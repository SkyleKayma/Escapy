package fr.skyle.escapy.data.repository.auth

import fr.skyle.escapy.data.repository.auth.api.AuthRemoteDataSource
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun signUpAsGuest() {
        authRemoteDataSource.signUpAsGuest()
    }

    override suspend fun signUp(
        email: String,
        password: String
    ) {
        authRemoteDataSource.signUp(
            email = email,
            password = password
        )
    }

    override suspend fun signIn(
        email: String,
        password: String
    ) {
        authRemoteDataSource.signIn(
            email = email,
            password = password
        )
    }

    override fun signOut() {
        authRemoteDataSource.signOut()
    }

    override suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String
    ) {
        authRemoteDataSource.sendMailForEmailProvider(
            newMail = newMail,
            currentPassword = currentPassword
        )
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ) {
        authRemoteDataSource.updatePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
    }

    override suspend fun deleteAccountFromEmailProvider(currentPassword: String) {
        authRemoteDataSource.deleteAccountFromEmailProvider(currentPassword)
    }

    override suspend fun deleteAccountFromAnonymousProvider() {
        authRemoteDataSource.deleteAccountFromAnonymousProvider()
    }

    override suspend fun linkAccountWithEmailProvider(
        email: String,
        password: String,
    ) {
        authRemoteDataSource.linkAccountWithEmailProvider(
            email = email,
            password = password
        )
    }
}