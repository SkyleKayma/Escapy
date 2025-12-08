package fr.skyle.escapy.data.repository.lobby

import androidx.room.withTransaction
import fr.skyle.escapy.data.db.ProjectDatabase
import fr.skyle.escapy.data.db.dao.LobbyDao
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.junction.LobbyParticipantCrossRef
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LobbyLocalDataSourceImpl @Inject constructor(
    private val database: ProjectDatabase,
    private val lobbyDao: LobbyDao
) : LobbyLocalDataSource {

    override suspend fun insertLobby(lobby: Lobby) {
        database.withTransaction {
            // Insert lobby
            lobbyDao.insertLobby(lobby)

            // Generate all crossRefs for all participants of lobby
            val crossRefs = lobby.participants.map { userId ->
                LobbyParticipantCrossRef(lobbyId = lobby.uid, userId = userId)
            }

            // Insert crossRefs
            lobbyDao.insertLobbyParticipantCrossRefs(crossRefs)
        }
    }

    override suspend fun insertLobbies(lobbies: List<Lobby>) {
        database.withTransaction {
            // Insert all lobbies
            lobbyDao.insertLobbies(lobbies)

            // Generate all crossRefs for all participants of all lobbies
            val crossRefs = lobbies.flatMap { lobby ->
                lobby.participants.map { userId ->
                    LobbyParticipantCrossRef(
                        lobbyId = lobby.uid,
                        userId = userId
                    )
                }
            }

            // Insert crossRefs
            lobbyDao.insertLobbyParticipantCrossRefs(crossRefs)
        }
    }

    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
        lobbyDao.watchLobby(lobbyId).distinctUntilChanged()

    override fun watchActiveLobbiesForUser(userId: String): Flow<List<Lobby>> =
        lobbyDao.watchLobbiesForUser(
            userId = userId,
            statuses = listOf(LobbyStatus.IN_PROGRESS, LobbyStatus.NOT_STARTED)
        ).distinctUntilChanged()

//    override fun watchLobby(lobbyId: String): Flow<Lobby?> =
//        lobbyDao.watchLobby(lobbyId).distinctUntilChanged()
//
}