package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface SignInAsGuestUseCase {
    suspend operator fun invoke()
}

class SignInAsGuestUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInAsGuestUseCase {

    override suspend fun invoke() {
        authRepository.signUpAsGuest().getOrThrow()
    }
}