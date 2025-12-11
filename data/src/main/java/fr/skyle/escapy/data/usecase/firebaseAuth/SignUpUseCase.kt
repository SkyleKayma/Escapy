package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface SignUpUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
    )
}

class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : SignUpUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ) {
        authRepository.signUp(
            email = email,
            password = password
        )
    }
}