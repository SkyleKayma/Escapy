package fr.skyle.escapy.data.usecase.account

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface DeleteAccountFromAnonymousProviderUseCase {
    suspend operator fun invoke(): DeleteAccountFromAnonymousProviderUseCaseResponse
}

class DeleteAccountFromAnonymousProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : DeleteAccountFromAnonymousProviderUseCase {

    override suspend fun invoke(): DeleteAccountFromAnonymousProviderUseCaseResponse {
        return try {
            authRepository.deleteAccountFromAnonymousProvider().getOrThrow()

            DeleteAccountFromAnonymousProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            DeleteAccountFromAnonymousProviderUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface DeleteAccountFromAnonymousProviderUseCaseResponse {
    data object Success : DeleteAccountFromAnonymousProviderUseCaseResponse
    data class Error(val message: String?) : DeleteAccountFromAnonymousProviderUseCaseResponse
}