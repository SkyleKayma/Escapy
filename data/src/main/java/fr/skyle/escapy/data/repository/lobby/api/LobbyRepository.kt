package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow

interface LobbyRepository {

    suspend fun createLobby(
        title: String,
        password: String?,
        duration: Long
    ): String

    suspend fun fetchLobby(lobbyId: String)

    suspend fun fetchLobbies(lobbyIds: List<String>)

    suspend fun fetchLobbiesForUser(userId: String)

    fun watchLobby(lobbyId: String): Flow<Lobby?>

    fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>>

    fun watchCurrentUserActiveLobbies(): Flow<List<Lobby>>

//    suspend fun joinLobby(lobbyId: String)
//
//    suspend fun leaveLobby(lobbyId: String)
//
//    suspend fun removeParticipant(lobbyId: String, participantId: String)
}