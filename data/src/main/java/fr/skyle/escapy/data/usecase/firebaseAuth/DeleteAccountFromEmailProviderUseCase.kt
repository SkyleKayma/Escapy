package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface DeleteAccountFromEmailProviderUseCase {
    suspend operator fun invoke(password: String)
}

class DeleteAccountFromEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : DeleteAccountFromEmailProviderUseCase {

    override suspend fun invoke(password: String) {
        authRepository.deleteAccountFromEmailProvider(password).getOrThrow()
    }
}