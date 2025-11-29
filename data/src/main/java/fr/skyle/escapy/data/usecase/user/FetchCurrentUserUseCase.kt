package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface FetchCurrentUserUseCase {
    suspend operator fun invoke(): FetchCurrentUserUseCaseResponse
}

class FetchCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchCurrentUserUseCase {

    override suspend fun invoke(): FetchCurrentUserUseCaseResponse {
        return try {
            userRepository.fetchCurrentUser().getOrThrow()

            FetchCurrentUserUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            FetchCurrentUserUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface FetchCurrentUserUseCaseResponse {
    data object Success : FetchCurrentUserUseCaseResponse
    data class Error(val message: String?) : FetchCurrentUserUseCaseResponse
}