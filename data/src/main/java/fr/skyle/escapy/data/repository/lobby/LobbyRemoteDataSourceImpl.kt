package fr.skyle.escapy.data.repository.lobby

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.repository.lobby.api.LobbyRemoteDataSource
import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.utils.model.FirebaseResponse
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.writeOnce
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRemoteDataSourceImpl @Inject constructor(
    private val dbRef: DatabaseReference,
) : LobbyRemoteDataSource {

    override suspend fun createLobby(request: CreateLobbyRequest): FirebaseResponse<Unit> {
        val ref = dbRef.child(FirebaseNode.Lobbies.path)

        // Generate new ID Firebase
        val newLobbyRef = ref.push()

        val dto = LobbyRequestDTO(
            lobbyTitle = request.title,
            password = request.password,
            duration = request.duration,
            createdAt = System.currentTimeMillis(),
            startedAt = null,
            endedAt = null,
            status = LobbyStatus.NOT_STARTED.name,
            createdBy = request.createdBy,
            participants = mapOf(request.createdBy to true)
        )

        return newLobbyRef.writeOnce(dto)
    }

    override suspend fun fetchLobby(lobbyId: String): FirebaseResponse<LobbyRequestDTO> =
        dbRef
            .child(FirebaseNode.Lobbies.path)
            .child(lobbyId)
            .readOnce(LobbyRequestDTO::class.java)

    override suspend fun fetchLobbiesForCurrentUser(
        currentUserId: String
    ): FirebaseResponse<Map<String, LobbyRequestDTO>> =
        dbRef
            .child(FirebaseNode.Lobbies.path)
            .readOnce(object : GenericTypeIndicator<Map<String, LobbyRequestDTO>>() {})
}