package fr.skyle.escapy.data.repository.auth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.data.repository.auth.api.AuthRemoteDataSource
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {

    override suspend fun signUpAsGuest(): Result<Unit> {
        return try {
            authRemoteDataSource.signUpAsGuest()

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            authRemoteDataSource.signUp(
                email = email,
                password = password
            )

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signIn(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            authRemoteDataSource.signIn(
                email = email,
                password = password
            )

            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun signOut() {
        authRemoteDataSource.signOut()
    }

    override suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String
    ): Result<Unit> {
        authRemoteDataSource.sendMailForEmailProvider(
            newMail = newMail,
            currentPassword = currentPassword
        )
        return Result.success(Unit)
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        authRemoteDataSource.updatePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
        return Result.success(Unit)
    }

    override suspend fun deleteAccountFromEmailProvider(currentPassword: String): Result<Unit> {
        authRemoteDataSource.deleteAccountFromEmailProvider(currentPassword)
        return Result.success(Unit)
    }

    override suspend fun deleteAccountFromAnonymousProvider(): Result<Unit> {
        authRemoteDataSource.deleteAccountFromAnonymousProvider()
        return Result.success(Unit)
    }
}