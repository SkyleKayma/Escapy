package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WatchLobbyUseCase {
    suspend operator fun invoke(lobbyId: String): Flow<Lobby?>
}

class WatchLobbyUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : WatchLobbyUseCase {

    override suspend fun invoke(lobbyId: String): Flow<Lobby?> =
        lobbyRepository.watchLobby(lobbyId)
}