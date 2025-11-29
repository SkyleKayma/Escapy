package fr.skyle.escapy.data.repository.auth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.data.repository.auth.api.FirebaseAuthRemoteDataSource
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuthRemoteDataSource: FirebaseAuthRemoteDataSource,
) : AuthRepository {

    override suspend fun signUpAsGuest(): Result<Unit> {
        return try {
            firebaseAuthRemoteDataSource.signUpAsGuest()

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
            firebaseAuthRemoteDataSource.signUp(
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
            firebaseAuthRemoteDataSource.signIn(
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
        firebaseAuthRemoteDataSource.signOut()
    }

    override suspend fun sendMailForEmailProvider(
        newMail: String,
        currentPassword: String
    ): Result<Unit> {
        firebaseAuthRemoteDataSource.sendMailForEmailProvider(
            newMail = newMail,
            currentPassword = currentPassword
        )
        return Result.success(Unit)
    }

    override suspend fun updatePassword(
        currentPassword: String,
        newPassword: String
    ): Result<Unit> {
        firebaseAuthRemoteDataSource.updatePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
        return Result.success(Unit)
    }

    override suspend fun deleteAccountFromEmailProvider(currentPassword: String): Result<Unit> {
        firebaseAuthRemoteDataSource.deleteAccountFromEmailProvider(currentPassword)
        return Result.success(Unit)
    }

    override suspend fun deleteAccountFromAnonymousProvider(): Result<Unit> {
        firebaseAuthRemoteDataSource.deleteAccountFromAnonymousProvider()
        return Result.success(Unit)
    }
}