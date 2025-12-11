package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface DeleteAccountFromAnonymousProviderUseCase {

    suspend operator fun invoke()
}

class DeleteAccountFromAnonymousProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : DeleteAccountFromAnonymousProviderUseCase {

    override suspend fun invoke() {
        authRepository.deleteAccountFromAnonymousProvider()
    }
}