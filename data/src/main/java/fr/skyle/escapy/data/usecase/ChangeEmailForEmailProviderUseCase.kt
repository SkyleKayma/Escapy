package fr.skyle.escapy.data.usecase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.ext.awaitWithTimeout
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface ChangeEmailForEmailProviderUseCase {
    suspend operator fun invoke(
        newEmail: String,
        currentPassword: String
    ): ChangeEmailForEmailProviderUseCaseResponse
}

class ChangeEmailForEmailProviderUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ChangeEmailForEmailProviderUseCase {

    override suspend fun invoke(
        newEmail: String,
        currentPassword: String
    ): ChangeEmailForEmailProviderUseCaseResponse {
        return try {
            val user = requireNotNull(firebaseAuth.currentUser)
            val email = requireNotNull(user.email)

            // Reauthenticate
            val credential = EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential).awaitWithTimeout()

            // Send verification email for update
            user.verifyBeforeUpdateEmail(newEmail).awaitWithTimeout()

            ChangeEmailForEmailProviderUseCaseResponse.EmailVerificationSent
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            ChangeEmailForEmailProviderUseCaseResponse.InvalidCurrent
        } catch (e: Exception) {
            Timber.e(e)
            ChangeEmailForEmailProviderUseCaseResponse.Error(e)
        }
    }
}

sealed interface ChangeEmailForEmailProviderUseCaseResponse {
    data object EmailVerificationSent : ChangeEmailForEmailProviderUseCaseResponse
    data object InvalidCurrent : ChangeEmailForEmailProviderUseCaseResponse
    data class Error(val exception: Exception) : ChangeEmailForEmailProviderUseCaseResponse
}
