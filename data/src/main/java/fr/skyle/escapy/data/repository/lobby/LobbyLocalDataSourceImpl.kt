package fr.skyle.escapy.data.repository.lobby

import fr.skyle.escapy.data.db.dao.LobbyDao
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.vo.Lobby
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyLocalDataSourceImpl @Inject constructor(
    private val lobbyDao: LobbyDao
) : LobbyLocalDataSource {

    override suspend fun insertLobby(lobby: Lobby) {
        lobbyDao.insert(lobby)
    }

    override suspend fun insertLobbies(lobbies: List<Lobby>) {
        lobbyDao.insertAll(lobbies)
    }

//    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
//        lobbyDao.watchLobby(lobbyId).distinctUntilChanged()
//
//    override fun watchActiveLobbies(): Flow<List<Lobby>> =
//        lobbyDao.watchActiveLobbies().distinctUntilChanged()
//
//    override fun watchLobbiesCreatedBy(uid: String): Flow<List<Lobby>> =
//        lobbyDao.watchLobbiesCreatedBy(uid).distinctUntilChanged()
//
//    override fun watchLobbiesJoinedBy(uid: String): Flow<List<Lobby>> =
//        lobbyDao.watchLobbiesJoinedBy(uid).distinctUntilChanged()
}