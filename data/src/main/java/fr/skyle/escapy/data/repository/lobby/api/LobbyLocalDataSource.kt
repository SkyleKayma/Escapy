package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow

interface LobbyLocalDataSource {
    suspend fun insertLobby(lobby: Lobby)

    suspend fun insertLobbies(lobbies: List<Lobby>)

    fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>>

//    fun watchLobby(lobbyId: String): Flow<Lobby?>


//    fun watchLobbiesCreatedBy(uid: String): Flow<List<Lobby>>
//
//    fun watchLobbiesJoinedBy(uid: String): Flow<List<Lobby>>
}