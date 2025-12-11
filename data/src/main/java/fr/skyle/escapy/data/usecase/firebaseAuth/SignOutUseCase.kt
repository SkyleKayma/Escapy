package fr.skyle.escapy.data.usecase.firebaseAuth

import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import javax.inject.Inject

interface SignOutUseCase {

    operator fun invoke()
}

class SignOutUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository
) : SignOutUseCase {

    override fun invoke() {
        authRepository.signOut()
    }
}