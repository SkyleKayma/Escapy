package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface SignInFromEmailProviderUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
    )
}

class SignInFromEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignInFromEmailProviderUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ) {
        authRepository.signIn(
            email = email,
            password = password
        )
    }
}