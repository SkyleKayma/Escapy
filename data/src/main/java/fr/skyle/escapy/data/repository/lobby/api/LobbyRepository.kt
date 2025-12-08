package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow

interface LobbyRepository {

    suspend fun createLobby(
        title: String,
        password: String?,
        duration: Long
    ): Result<String>

    suspend fun fetchLobby(lobbyId: String): Result<Unit>

    suspend fun fetchLobbies(lobbyIds: List<String>): Result<Unit>

    suspend fun fetchLobbiesForUser(userId: String): Result<Unit>

    fun watchLobby(lobbyId: String): Flow<Lobby?>

    fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>>

    fun watchCurrentUserActiveLobbies(): Flow<List<Lobby>>

//    suspend fun joinLobby(lobbyId: String): Result<Unit>
//
//    suspend fun leaveLobby(lobbyId: String): Result<Unit>
//
//    suspend fun removeParticipant(lobbyId: String, participantId: String): Result<Unit>
}