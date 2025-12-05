package fr.skyle.escapy.data.repository.lobby.api

import fr.skyle.escapy.data.vo.Lobby

interface LobbyLocalDataSource {
    suspend fun insertLobby(lobby: Lobby)

    suspend fun insertLobbies(lobbies: List<Lobby>)

//    fun watchLobby(lobbyId: String): Flow<Lobby?>
//
//    fun watchActiveLobbies(): Flow<List<Lobby>>
//
//    fun watchLobbiesCreatedBy(uid: String): Flow<List<Lobby>>
//
//    fun watchLobbiesJoinedBy(uid: String): Flow<List<Lobby>>
}