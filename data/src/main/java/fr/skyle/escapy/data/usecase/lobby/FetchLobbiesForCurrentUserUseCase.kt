package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface FetchLobbiesForCurrentUserUseCase {
    suspend operator fun invoke(): FetchLobbiesForCurrentUserUseCaseResponse
}

class FetchLobbiesForCurrentUserUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : FetchLobbiesForCurrentUserUseCase {

    override suspend fun invoke(): FetchLobbiesForCurrentUserUseCaseResponse {
        return try {
            lobbyRepository.fetchLobbiesForCurrentUser().getOrThrow()

            FetchLobbiesForCurrentUserUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            FetchLobbiesForCurrentUserUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface FetchLobbiesForCurrentUserUseCaseResponse {
    data object Success : FetchLobbiesForCurrentUserUseCaseResponse
    data class Error(val message: String?) : FetchLobbiesForCurrentUserUseCaseResponse
}