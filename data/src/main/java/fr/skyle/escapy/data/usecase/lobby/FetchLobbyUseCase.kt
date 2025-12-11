package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import javax.inject.Inject

interface FetchLobbyUseCase {
    
    suspend operator fun invoke(lobbyId: String)
}

class FetchLobbyUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : FetchLobbyUseCase {

    override suspend fun invoke(lobbyId: String) {
        lobbyRepository.fetchLobby(lobbyId)
    }
}