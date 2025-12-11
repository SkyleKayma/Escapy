package fr.skyle.escapy.data.usecase.lobby

import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface ValidateLobbyQRCodeUseCase {

    suspend operator fun invoke(
        lobbyId: String,
        password: String,
    )
}

class ValidateLobbyQRCodeUseCaseImpl @Inject constructor(
    private val fetchLobbyUseCase: FetchLobbyUseCase,
    private val watchLobbyUseCase: WatchLobbyUseCase,
) : ValidateLobbyQRCodeUseCase {

    override suspend fun invoke(
        lobbyId: String,
        password: String,
    ) {
        // Fetch updated lobby
        fetchLobbyUseCase(lobbyId)

        // Get lobby locally
        val lobby = watchLobbyUseCase(lobbyId).first() ?: throw Exception("Lobby not found")

        // Check password
        if (lobby.password != password) {
            throw ValidateLobbyQRCodeUseCaseInvalidQRCode()
        }
    }
}

class ValidateLobbyQRCodeUseCaseInvalidQRCode() : Exception("Invalid password")