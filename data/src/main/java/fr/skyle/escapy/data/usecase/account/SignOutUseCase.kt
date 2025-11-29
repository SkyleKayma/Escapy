package fr.skyle.escapy.data.usecase.account

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface SignOutUseCase {
    operator fun invoke(): SignOutUseCaseResponse
}

class SignOutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignOutUseCase {

    override fun invoke(): SignOutUseCaseResponse {
        authRepository.signOut()

        return SignOutUseCaseResponse.Success
    }
}

sealed interface SignOutUseCaseResponse {
    data object Success : SignOutUseCaseResponse
}