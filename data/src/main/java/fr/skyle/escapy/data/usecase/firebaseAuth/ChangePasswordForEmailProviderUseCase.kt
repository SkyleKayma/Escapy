package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface ChangePasswordForEmailProviderUseCase {

    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    )
}

class ChangePasswordForEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : ChangePasswordForEmailProviderUseCase {

    override suspend fun invoke(
        currentPassword: String,
        newPassword: String
    ) {
        authRepository.updatePassword(
            currentPassword = currentPassword,
            newPassword = newPassword
        )
    }
}