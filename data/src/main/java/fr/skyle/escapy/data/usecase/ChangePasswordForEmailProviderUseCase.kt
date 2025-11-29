package fr.skyle.escapy.data.usecase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.ext.awaitWithTimeout
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

interface ChangePasswordForEmailProviderUseCase {
    suspend operator fun invoke(
        currentPassword: String,
        newPassword: String
    ): ChangePasswordForEmailProviderUseCaseResponse
}

class ChangePasswordForEmailProviderUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ChangePasswordForEmailProviderUseCase {

    override suspend fun invoke(
        currentPassword: String,
        newPassword: String
    ): ChangePasswordForEmailProviderUseCaseResponse {
        return try {
            val user = requireNotNull(firebaseAuth.currentUser)
            val email = requireNotNull(user.email)

            // Reauthenticate
            val credential = EmailAuthProvider.getCredential(email, currentPassword)
            user.reauthenticate(credential).awaitWithTimeout()

            // Update password
            user.updatePassword(newPassword).awaitWithTimeout()

            ChangePasswordForEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            ChangePasswordForEmailProviderUseCaseResponse.InvalidCurrentPassword
        } catch (e: Exception) {
            Timber.e(e)
            ChangePasswordForEmailProviderUseCaseResponse.Error(e)
        }
    }
}

sealed interface ChangePasswordForEmailProviderUseCaseResponse {
    data object Success : ChangePasswordForEmailProviderUseCaseResponse
    data object InvalidCurrentPassword : ChangePasswordForEmailProviderUseCaseResponse
    data class Error(val exception: Exception) : ChangePasswordForEmailProviderUseCaseResponse
}