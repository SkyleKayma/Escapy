package fr.skyle.escapy.data.usecase.account

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface SignInFromEmailProviderUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): SignInFromEmailProviderUseCaseResponse
}

class SignInFromEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInFromEmailProviderUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ): SignInFromEmailProviderUseCaseResponse {
        return try {
            authRepository.signIn(
                email = email,
                password = password
            ).getOrThrow()

            SignInFromEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            SignInFromEmailProviderUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface SignInFromEmailProviderUseCaseResponse {
    data object Success : SignInFromEmailProviderUseCaseResponse
    data class Error(val message: String?) : SignInFromEmailProviderUseCaseResponse
}
