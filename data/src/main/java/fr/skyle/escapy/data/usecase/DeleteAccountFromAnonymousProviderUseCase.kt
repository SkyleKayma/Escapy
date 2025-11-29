package fr.skyle.escapy.data.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import fr.skyle.escapy.data.ext.awaitWithTimeout
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface DeleteAccountFromAnonymousProviderUseCase {
    suspend operator fun invoke(): DeleteAccountFromAnonymousProviderUseCaseResponse
}

class DeleteAccountFromAnonymousProviderUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : DeleteAccountFromAnonymousProviderUseCase {

    override suspend fun invoke(): DeleteAccountFromAnonymousProviderUseCaseResponse {
        return try {
            val user = requireNotNull(firebaseAuth.currentUser)

            // Delete the account
            user.delete().awaitWithTimeout()

            DeleteAccountFromAnonymousProviderUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            Timber.e(e)
            DeleteAccountFromAnonymousProviderUseCaseResponse.InvalidFields
        } catch (e: Exception) {
            Timber.e(e)
            DeleteAccountFromAnonymousProviderUseCaseResponse.Error(e)
        }
    }
}

sealed interface DeleteAccountFromAnonymousProviderUseCaseResponse {
    data object Success : DeleteAccountFromAnonymousProviderUseCaseResponse
    data object InvalidFields : DeleteAccountFromAnonymousProviderUseCaseResponse
    data class Error(val exception: Exception) : DeleteAccountFromAnonymousProviderUseCaseResponse
}