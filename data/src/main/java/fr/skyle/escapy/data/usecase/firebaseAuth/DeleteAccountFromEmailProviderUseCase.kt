package fr.skyle.escapy.data.usecase.firebaseAuth

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface DeleteAccountFromEmailProviderUseCase {
    suspend operator fun invoke(
        password: String,
    ): DeleteAccountFromEmailProviderUseCaseResponse
}

class DeleteAccountFromEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : DeleteAccountFromEmailProviderUseCase {

    override suspend fun invoke(
        password: String,
    ): DeleteAccountFromEmailProviderUseCaseResponse {
        return try {
            authRepository.deleteAccountFromEmailProvider(password).getOrThrow()

            DeleteAccountFromEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            DeleteAccountFromEmailProviderUseCaseResponse.InvalidCurrentPassword
        } catch (e: Exception) {
            Timber.e(e)
            DeleteAccountFromEmailProviderUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface DeleteAccountFromEmailProviderUseCaseResponse {
    data object Success : DeleteAccountFromEmailProviderUseCaseResponse
    data object InvalidCurrentPassword : DeleteAccountFromEmailProviderUseCaseResponse
    data class Error(val message: String?) : DeleteAccountFromEmailProviderUseCaseResponse
}