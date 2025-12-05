package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import kotlinx.coroutines.CancellationException
import timber.log.Timber
import javax.inject.Inject

interface FetchLobbyUseCase {
    suspend operator fun invoke(
        lobbyId: String
    ): FetchLobbyUseCaseResponse
}

class FetchLobbyUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : FetchLobbyUseCase {

    override suspend fun invoke(
        lobbyId: String
    ): FetchLobbyUseCaseResponse {
        return try {
            lobbyRepository.fetchLobby(lobbyId).getOrThrow()

            FetchLobbyUseCaseResponse.Success
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Timber.e(e)
            FetchLobbyUseCaseResponse.Error(e.message)
        }
    }
}

sealed interface FetchLobbyUseCaseResponse {
    data object Success : FetchLobbyUseCaseResponse
    data class Error(val message: String?) : FetchLobbyUseCaseResponse
}