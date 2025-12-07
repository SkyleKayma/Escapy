package fr.skyle.escapy.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.data.vo.junction.LobbyParticipantCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface LobbyDao {

    // Insert

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLobby(lobby: Lobby)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLobbies(lobbies: List<Lobby>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLobbyParticipantCrossRefs(lobbyParticipantCrossRefs: List<LobbyParticipantCrossRef>)

    // Read

    @Query("SELECT * FROM Lobby WHERE uid = :lobbyId LIMIT 1")
    fun watchLobby(lobbyId: String): Flow<Lobby?>

    @Transaction
    @Query(
        """
        SELECT Lobby.* FROM Lobby
        INNER JOIN LobbyParticipantCrossRef
        ON Lobby.uid = LobbyParticipantCrossRef.lobbyId
        WHERE LobbyParticipantCrossRef.userId = :userId
        AND Lobby.status IN (:statuses)
        """
    )
    fun watchLobbiesForUser(
        userId: String,
        statuses: List<LobbyStatus>
    ): Flow<List<Lobby>>

    // Delete

    @Delete
    suspend fun delete(lobby: Lobby)
}