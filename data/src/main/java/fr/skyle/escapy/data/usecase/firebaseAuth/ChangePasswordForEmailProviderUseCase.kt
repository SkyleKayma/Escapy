package fr.skyle.escapy.data.usecase.firebaseAuth

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface ChangePasswordForEmailProviderUseCase {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    ): ChangePasswordForEmailProviderUseCaseResponse
}

class ChangePasswordForEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ChangePasswordForEmailProviderUseCase {

    override suspend fun invoke(
        currentPassword: String,
        newPassword: String
    ): ChangePasswordForEmailProviderUseCaseResponse {
        return try {
            authRepository.updatePassword(
                currentPassword = currentPassword,
                newPassword = newPassword
            ).getOrThrow()

            ChangePasswordForEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            ChangePasswordForEmailProviderUseCaseResponse.InvalidCurrentPassword
        } catch (e: Exception) {
            Timber.e(e)
            ChangePasswordForEmailProviderUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface ChangePasswordForEmailProviderUseCaseResponse {
    data object Success : ChangePasswordForEmailProviderUseCaseResponse
    data object InvalidCurrentPassword : ChangePasswordForEmailProviderUseCaseResponse
    data class Error(val message: String?) : ChangePasswordForEmailProviderUseCaseResponse
}