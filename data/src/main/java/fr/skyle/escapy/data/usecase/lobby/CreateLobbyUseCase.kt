package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import javax.inject.Inject
import kotlin.random.Random

interface CreateLobbyUseCase {

    suspend operator fun invoke(
        title: String,
        duration: Long
    ): String
}

class CreateLobbyUseCaseImpl @Inject constructor(
    private val lobbyRepository: LobbyRepository
) : CreateLobbyUseCase {

    override suspend fun invoke(
        title: String,
        duration: Long
    ): String {
        val password = Random.nextInt(1000, 10000).toString()

        val lobbyId = lobbyRepository.createLobby(
            title = title,
            password = password,
            duration = duration
        )

        return lobbyId
    }
}