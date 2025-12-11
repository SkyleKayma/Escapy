package fr.skyle.escapy.data.repository.lobby

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.repository.lobby.api.LobbyRemoteDataSource
import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.updateOnce
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.mapper.toRequestDTO
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRemoteDataSourceImpl @Inject constructor(
    private val dbRef: DatabaseReference,
) : LobbyRemoteDataSource {

    override suspend fun createLobby(request: CreateLobbyRequest): String {
        val lobbyRef = dbRef.child(FirebaseNode.LOBBIES)

        // Generate new ID Firebase
        val newLobbyRef = lobbyRef.push()
        val lobbyId = newLobbyRef.key ?: throw Exception("Failed to generate lobby ID")

        val lobby = Lobby(
            uid = lobbyId,
            title = request.title,
            password = request.password,
            duration = request.duration,
            createdAt = System.currentTimeMillis(),
            startedAt = null,
            endedAt = null,
            status = LobbyStatus.NOT_STARTED,
            createdBy = request.createdBy,
            createdByName = request.createdByName,
            participants = listOf(request.createdBy)
        )

        // Multi-path update
        val updates = hashMapOf(
            "/${FirebaseNode.LOBBIES}/$lobbyId" to lobby.toRequestDTO(),
            "/${FirebaseNode.USER_LOBBIES}/${request.createdBy}/$lobbyId" to true
        )

        // Update
        dbRef.updateOnce(updates)

        return lobbyId
    }

    override suspend fun fetchLobby(lobbyId: String): LobbyRequestDTO? =
        dbRef
            .child(FirebaseNode.LOBBIES)
            .child(lobbyId)
            .readOnce(LobbyRequestDTO::class.java)

    override suspend fun fetchUserLobbyIds(userId: String): Map<String, Boolean>? =
        dbRef.child(FirebaseNode.USER_LOBBIES)
            .child(userId)
            .readOnce(object : GenericTypeIndicator<Map<String, Boolean>>() {})
}