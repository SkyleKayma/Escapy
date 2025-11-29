package fr.skyle.escapy.data.usecase

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface SignUpUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): SignUpUseCaseResponse
}

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignUpUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ): SignUpUseCaseResponse {
        return try {
            authRepository.signUp(
                email = email,
                password = password
            ).getOrThrow()

            SignUpUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            SignUpUseCaseResponse.Error(e)
        }
    }
}

sealed interface SignUpUseCaseResponse {
    data object Success : SignUpUseCaseResponse
    data class Error(val exception: Exception) : SignUpUseCaseResponse
}
