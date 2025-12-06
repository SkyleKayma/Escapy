package fr.skyle.escapy.data.repository.lobby

import fr.skyle.escapy.data.db.dao.LobbyDao
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    override fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>> =
        lobbyDao.watchLobbiesForUser(
            userId = userId,
            statuses = listOf(LobbyStatus.IN_PROGRESS, LobbyStatus.NOT_STARTED)
        ).distinctUntilChanged()

//    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
//        lobbyDao.watchLobby(lobbyId).distinctUntilChanged()
//

//    override fun watchLobbiesCreatedBy(uid: String): Flow<List<Lobby>> =
//        lobbyDao.watchLobbiesCreatedBy(uid).distinctUntilChanged()
//
//    override fun watchLobbiesJoinedBy(uid: String): Flow<List<Lobby>> =
//        lobbyDao.watchLobbiesJoinedBy(uid).distinctUntilChanged()
}