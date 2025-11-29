package fr.skyle.escapy.data.usecase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

interface SignOutUseCase {
    operator fun invoke(): SignOutUseCaseResponse
}

class SignOutUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : SignOutUseCase {

    override fun invoke(): SignOutUseCaseResponse {
        firebaseAuth.signOut()

        return SignOutUseCaseResponse.Success
    }
}

sealed interface SignOutUseCaseResponse {
    data object Success : SignOutUseCaseResponse
}