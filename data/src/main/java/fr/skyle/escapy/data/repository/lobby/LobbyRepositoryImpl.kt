package fr.skyle.escapy.data.repository.lobby

import com.google.firebase.auth.FirebaseAuth
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRemoteDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.mapper.toLobby
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val lobbyRemoteDataSource: LobbyRemoteDataSource,
    private val lobbyLocalDataSource: LobbyLocalDataSource,
    private val userLocalDataSource: UserLocalDataSource
) : LobbyRepository {

    override suspend fun createLobby(
        title: String,
        password: String?,
        duration: Long
    ): String {
        val user = firebaseAuth.currentUser
            ?: throw Exception("No current user")

        val userLocal = userLocalDataSource.getUser(user.uid)
            ?: throw Exception("No current local user")

        val request = CreateLobbyRequest(
            title = title,
            password = password,
            duration = duration,
            createdBy = user.uid,
            createdByName = userLocal.username,
        )

        val lobbyId = lobbyRemoteDataSource.createLobby(request)
        return lobbyId
    }

    override suspend fun fetchLobby(lobbyId: String) {
        val lobbyRequestDTO = lobbyRemoteDataSource.fetchLobby(lobbyId)

        lobbyRequestDTO?.let {
            lobbyLocalDataSource.insertLobby(it.toLobby(lobbyId))
        }
    }

    override suspend fun fetchLobbies(lobbyIds: List<String>) {
        coroutineScope {
            val result = mutableMapOf<String, LobbyRequestDTO>()

            val jobs = lobbyIds.map { lobbyId ->
                async {
                    fetchLobby(lobbyId)
                }
            }

            jobs.awaitAll()

            val lobbies = result.map { (id, dto) ->
                dto.toLobby(id)
            }
            lobbyLocalDataSource.insertLobbies(lobbies)
        }
    }

    override suspend fun fetchLobbiesForUser(userId: String) {
        // Fetch user lobby ids
        val lobbyIds = lobbyRemoteDataSource.fetchUserLobbyIds(userId)?.map { it.key } ?: listOf()

        // Fetch lobbies based on ids
        fetchLobbies(lobbyIds)
    }

    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
        lobbyLocalDataSource.watchLobby(lobbyId)

    override fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>> =
        lobbyLocalDataSource.watchActiveLobbiesForUser(userId)

    override fun watchCurrentUserActiveLobbies(): Flow<List<Lobby>> {
        val user = firebaseAuth.currentUser ?: throw Exception("No current user")
        return watchActiveLobbiesForUser(user.uid)
    }

//    override suspend fun joinLobby(lobbyId: String) {
//        val user = firebaseAuth.currentUser ?: return Result.failure(Exception("Not authenticated"))
//
//        val response = lobbyRemoteDataSource.joinLobby(
//            lobbyId = lobbyId,
//            userId = user.uid
//        )
//
//        val body = response.body
//        return if (response.isSuccessful && body != null) {
//            lobbyLocalDataSource.insertLobby(body.toLobbyEntity())
//            Result.success(Unit)
//        } else {
//            Result.failure(Exception(response.exception))
//        }
//    }
//
//    override suspend fun leaveLobby(lobbyId: String) {
//        val user = firebaseAuth.currentUser ?: return Result.failure(Exception("Not authenticated"))
//
//        val response = lobbyRemoteDataSource.leaveLobby(
//            lobbyId = lobbyId,
//            userId = user.uid
//        )
//
//        val body = response.body
//        return if (response.isSuccessful && body != null) {
//            lobbyLocalDataSource.insertLobby(body.toLobbyEntity())
//            Result.success(Unit)
//        } else {
//            Result.failure(Exception(response.exception))
//        }
//    }
//
//    override suspend fun removeParticipant(lobbyId: String, participantId: String) {
//        val user = firebaseAuth.currentUser ?: return Result.failure(Exception("Not authenticated"))
//
//        val response = lobbyRemoteDataSource.removeParticipant(
//            lobbyId = lobbyId,
//            masterId = user.uid,
//            playerId = participantId
//        )
//
//        val body = response.body
//        return if (response.isSuccessful && body != null) {
//            lobbyLocalDataSource.insertLobby(body.toLobbyEntity())
//            Result.success(Unit)
//        } else {
//            Result.failure(Exception(response.exception))
//        }
//    }
//
//    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
//        lobbyLocalDataSource.watchLobby(lobbyId)
}