package fr.skyle.escapy.data.usecase.lobby

import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import fr.skyle.escapy.data.repository.user.api.UserRepository
import javax.inject.Inject

interface FetchLobbiesForCurrentUserUseCase {

    suspend operator fun invoke()
}

class FetchLobbiesForCurrentUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository,
    private val lobbyRepository: LobbyRepository,
) : FetchLobbiesForCurrentUserUseCase {

    override suspend fun invoke() {
        val user = userRepository.getCurrentUser() ?: throw Exception("User not found")

        lobbyRepository.fetchLobbiesForUser(user.uid)
    }
}