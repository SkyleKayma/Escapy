package fr.skyle.escapy.data.usecase.user

import fr.skyle.escapy.data.repository.user.api.UserRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface FetchUserUseCase {
    suspend operator fun invoke(
        userId: String
    ): FetchUserUseCaseResponse
}

class FetchUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : FetchUserUseCase {

    override suspend fun invoke(
        userId: String
    ): FetchUserUseCaseResponse {
        return try {
            userRepository.fetchUser(userId).getOrThrow()

            FetchUserUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            FetchUserUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface FetchUserUseCaseResponse {
    data object Success : FetchUserUseCaseResponse
    data class Error(val message: String?) : FetchUserUseCaseResponse
}