package fr.skyle.escapy.data.usecase.firebaseAuth

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface LinkAccountWithEmailProviderUseCase {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): LinkAccountWithEmailProviderUseCaseResponse
}

class LinkAccountWithEmailProviderUseCaseImpl @Inject constructor(
    private val authRepository: AuthRepository,
) : LinkAccountWithEmailProviderUseCase {

    override suspend fun invoke(
        email: String,
        password: String,
    ): LinkAccountWithEmailProviderUseCaseResponse {
        return try {
            authRepository.linkAccountWithEmailProvider(
                email = email,
                password = password
            ).getOrThrow()

            LinkAccountWithEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthUserCollisionException) {
            Timber.e(e)
            LinkAccountWithEmailProviderUseCaseResponse.EmailAlreadyUsed
        } catch (e: Exception) {
            Timber.e(e)
            LinkAccountWithEmailProviderUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface LinkAccountWithEmailProviderUseCaseResponse {
    data object Success : LinkAccountWithEmailProviderUseCaseResponse
    data object EmailAlreadyUsed : LinkAccountWithEmailProviderUseCaseResponse
    data class Error(val message: String?) : LinkAccountWithEmailProviderUseCaseResponse
}
