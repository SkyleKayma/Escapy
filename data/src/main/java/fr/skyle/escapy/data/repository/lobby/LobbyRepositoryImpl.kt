package fr.skyle.escapy.data.repository.lobby

import com.google.firebase.auth.FirebaseAuth
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRemoteDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository
import fr.skyle.escapy.data.repository.lobby.model.CreateLobbyRequest
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.mapper.toLobby
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val lobbyRemoteDataSource: LobbyRemoteDataSource,
    private val lobbyLocalDataSource: LobbyLocalDataSource,
) : LobbyRepository {

    override suspend fun createLobby(
        title: String,
        password: String?,
        duration: Long
    ): Result<Unit> {
        val user = firebaseAuth.currentUser
            ?: throw Exception("No current user")

        val request = CreateLobbyRequest(
            title = title,
            password = password,
            duration = duration,
            createdBy = user.uid
        )

        val response = lobbyRemoteDataSource.createLobby(request)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Exception(response.exception))
        }
    }

    override suspend fun fetchLobby(lobbyId: String): Result<Unit> {
        val response = lobbyRemoteDataSource.fetchLobby(lobbyId)

        val body = response.body
        return if (response.isSuccessful && body != null) {
            lobbyLocalDataSource.insertLobby(body.toLobby(lobbyId))
            Result.success(Unit)
        } else {
            Result.failure(Exception(response.exception))
        }
    }

    override suspend fun fetchLobbies(lobbyIds: List<String>): Result<Unit> {
        val response = lobbyRemoteDataSource.fetchLobbies(lobbyIds)

        val body = response.body
        return if (response.isSuccessful && body != null) {
            val lobbies = body.map { (id, dto) ->
                dto.toLobby(id)
            }

            lobbyLocalDataSource.insertLobbies(lobbies)

            Result.success(Unit)
        } else {
            Result.failure(Exception(response.exception))
        }
    }

    override suspend fun fetchLobbiesForUser(userId: String): Result<Unit> {
        // Fetch user lobby ids
        val fetchUserLobbyIdsResponse = lobbyRemoteDataSource.fetchUserLobbyIds(userId)

        val fetchUserLobbyIdsBody = fetchUserLobbyIdsResponse.body
        if (!fetchUserLobbyIdsResponse.isSuccessful || fetchUserLobbyIdsBody.isNullOrEmpty()) {
            return Result.failure(Exception(fetchUserLobbyIdsResponse.exception))
        }

        // Fetch lobbies based on ids
        val lobbyIds = fetchUserLobbyIdsBody.keys.toList()
        val fetchLobbyIdsResponse = lobbyRemoteDataSource.fetchLobbies(lobbyIds)

        val fetchLobbyIdsBody = fetchLobbyIdsResponse.body
        return if (fetchLobbyIdsResponse.isSuccessful && fetchLobbyIdsBody != null) {
            val lobbies = fetchLobbyIdsBody.map { (id, dto) ->
                dto.toLobby(id)
            }

            // Insert lobbies into Room
            lobbyLocalDataSource.insertLobbies(lobbies)

            Result.success(Unit)
        } else {
            Result.failure(Exception(fetchLobbyIdsResponse.exception))
        }
    }

    override fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>> {
        return lobbyLocalDataSource.watchActiveLobbiesForUser(userId)
    }

    override fun watchCurrentUserActiveLobbies(): Flow<List<Lobby>> {
        val user = firebaseAuth.currentUser ?: throw Exception("No current user")
        return watchActiveLobbiesForUser(user.uid)
    }

//    override suspend fun joinLobby(lobbyId: String): Result<Unit> {
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
//    override suspend fun leaveLobby(lobbyId: String): Result<Unit> {
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
//    override suspend fun removeParticipant(lobbyId: String, participantId: String): Result<Unit> {
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