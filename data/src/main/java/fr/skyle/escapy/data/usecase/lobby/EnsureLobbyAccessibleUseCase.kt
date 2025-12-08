package fr.skyle.escapy.data.usecase.lobby

import com.google.firebase.auth.FirebaseAuth
import fr.skyle.escapy.data.ext.isUserInLobby
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface EnsureLobbyAccessibleUseCase {
    suspend operator fun invoke(lobbyId: String)
}

class EnsureLobbyAccessibleUseCaseImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fetchLobbyUseCase: FetchLobbyUseCase,
    private val watchLobbyUseCase: WatchLobbyUseCase,
) : EnsureLobbyAccessibleUseCase {

    override suspend fun invoke(lobbyId: String) {
        fetchLobbyUseCase(lobbyId)

        val lobby = watchLobbyUseCase(lobbyId).first()
            ?: throw EnsureLobbyAccessibleUseCaseLobbyDoesNotExist()

        val user = firebaseAuth.currentUser ?: throw Exception("No current user")

        if (!lobby.isUserInLobby(user.uid)) {
            throw EnsureLobbyAccessibleUseCaseNotInLobby()
        }
    }
}

class EnsureLobbyAccessibleUseCaseLobbyDoesNotExist() :
    Exception("The lobby is not existing anymore")

class EnsureLobbyAccessibleUseCaseNotInLobby() :
    Exception("The user is not part of the lobby")