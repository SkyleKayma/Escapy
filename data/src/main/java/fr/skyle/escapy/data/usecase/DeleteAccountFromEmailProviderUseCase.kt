package fr.skyle.escapy.data.usecase

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.ext.awaitWithTimeout
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface DeleteAccountFromEmailProviderUseCase {
    suspend operator fun invoke(
        password: String,
    ): DeleteAccountFromEmailProviderUseCaseResponse
}

class DeleteAccountFromEmailProviderUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : DeleteAccountFromEmailProviderUseCase {

    override suspend fun invoke(
        password: String,
    ): DeleteAccountFromEmailProviderUseCaseResponse {
        return try {
            val user = requireNotNull(firebaseAuth.currentUser)
            val email = requireNotNull(user.email)

            // Need to reauthenticate
            val credential = EmailAuthProvider.getCredential(email, password)
            user.reauthenticate(credential).awaitWithTimeout()

            // Then delete the account
            user.delete().awaitWithTimeout()

            DeleteAccountFromEmailProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            DeleteAccountFromEmailProviderUseCaseResponse.ErrorInvalidFields
        } catch (e: Exception) {
            Timber.e(e)
            DeleteAccountFromEmailProviderUseCaseResponse.Error(e)
        }
    }
}

sealed class DeleteAccountFromEmailProviderUseCaseResponse {
    data object Success : DeleteAccountFromEmailProviderUseCaseResponse()
    data object ErrorInvalidFields : DeleteAccountFromEmailProviderUseCaseResponse()
    data class Error(val exception: Exception) : DeleteAccountFromEmailProviderUseCaseResponse()
}