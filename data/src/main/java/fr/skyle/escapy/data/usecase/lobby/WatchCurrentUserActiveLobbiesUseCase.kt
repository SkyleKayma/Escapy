package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface WatchCurrentUserActiveLobbiesUseCase {

    suspend operator fun invoke(): Flow<List<Lobby>>
}

class WatchCurrentUserActiveLobbiesUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : WatchCurrentUserActiveLobbiesUseCase {

    override suspend fun invoke(): Flow<List<Lobby>> =
        lobbyRepository.watchCurrentUserActiveLobbies()
}