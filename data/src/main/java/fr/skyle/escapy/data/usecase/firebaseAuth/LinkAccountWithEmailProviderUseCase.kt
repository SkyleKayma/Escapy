package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface LinkAccountWithEmailProviderUseCase {

    suspend operator fun invoke(
        email: String,
        password: String,
    )
}

class LinkAccountWithEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : LinkAccountWithEmailProviderUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ) {
        authRepository.linkAccountWithEmailProvider(
            email = email,
            password = password
        )
    }
}