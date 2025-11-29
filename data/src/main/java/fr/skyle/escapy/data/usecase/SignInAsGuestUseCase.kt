package fr.skyle.escapy.data.usecase

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface SignInAsGuestUseCase {
    suspend operator fun invoke(): SignInAsGuestUseCaseResponse
}

class SignInAsGuestUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInAsGuestUseCase {

    override suspend fun invoke(): SignInAsGuestUseCaseResponse {
        return try {
            authRepository.signUpAsGuest().getOrThrow()

            SignInAsGuestUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            SignInAsGuestUseCaseResponse.Error(e)
        }
    }
}

sealed interface SignInAsGuestUseCaseResponse {
    data object Success : SignInAsGuestUseCaseResponse
    data class Error(val exception: Exception) : SignInAsGuestUseCaseResponse
}
