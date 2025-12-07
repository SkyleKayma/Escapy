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
import fr.skyle.escapy.data.utils.updateChildrenOnce
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRemoteDataSourceImpl @Inject constructor(
    private val dbRef: DatabaseReference,
) : LobbyRemoteDataSource {

    override suspend fun createLobby(request: CreateLobbyRequest): FirebaseResponse<Unit> {
        val lobbyRef = dbRef.child(FirebaseNode.LOBBIES)

        // Generate new ID Firebase
        val newLobbyRef = lobbyRef.push()
        val lobbyId = newLobbyRef.key ?: return FirebaseResponse(
            null,
            Exception("Failed to generate lobby ID")
        )

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

        // Multi-path update
        val updates = hashMapOf(
            "${FirebaseNode.LOBBIES}/$lobbyId" to dto,
            "/userLobbies/${request.createdBy}/$lobbyId" to true
        )

        return dbRef.updateChildrenOnce(updates)
    }

    override suspend fun fetchLobby(lobbyId: String): FirebaseResponse<LobbyRequestDTO> =
        dbRef
            .child(FirebaseNode.LOBBIES)
            .child(lobbyId)
            .readOnce(LobbyRequestDTO::class.java)

    override suspend fun fetchLobbies(
        lobbyIds: List<String>
    ): FirebaseResponse<Map<String, LobbyRequestDTO>> = coroutineScope {
        val result = mutableMapOf<String, LobbyRequestDTO>()

        val jobs = lobbyIds.map { lobbyId ->
            async {
                val response = fetchLobby(lobbyId)

                if (response.isSuccessful && response.body != null) {
                    result[lobbyId] = response.body
                } else if (!response.isSuccessful) {
                    throw response.exception ?: Exception("Unknown Firebase error")
                }
            }
        }

        return@coroutineScope try {
            jobs.awaitAll()
            FirebaseResponse(result, null)
        } catch (e: Exception) {
            FirebaseResponse(null, e)
        }
    }

    override suspend fun fetchUserLobbyIds(userId: String): FirebaseResponse<Map<String, Boolean>> =
        dbRef.child(FirebaseNode.USER_LOBBIES)
            .child(userId)
            .readOnce(object : GenericTypeIndicator<Map<String, Boolean>>() {})
}