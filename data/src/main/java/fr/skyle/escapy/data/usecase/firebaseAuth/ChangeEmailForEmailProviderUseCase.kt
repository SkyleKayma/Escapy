package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface ChangeEmailForEmailProviderUseCase {

    suspend operator fun invoke(
        newEmail: String,
        currentPassword: String
    )
}

class ChangeEmailForEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : ChangeEmailForEmailProviderUseCase {

    override suspend fun invoke(
        newEmail: String,
        currentPassword: String
    ) {
        authRepository.sendMailForEmailProvider(
            newMail = newEmail,
            currentPassword = currentPassword
        )
    }
}