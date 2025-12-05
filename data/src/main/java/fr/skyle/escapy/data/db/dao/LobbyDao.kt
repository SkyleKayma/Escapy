package fr.skyle.escapy.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import fr.skyle.escapy.data.vo.Lobby
import kotlinx.coroutines.flow.Flow

@Dao
interface LobbyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(lobby: Lobby)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(lobbies: List<Lobby>)

    @Query("SELECT * FROM Lobby WHERE uid = :lobbyId LIMIT 1")
    fun watchLobby(lobbyId: String): Flow<Lobby?>

    @Query("SELECT * FROM Lobby WHERE status = 'ACTIVE'")
    fun watchActiveLobbies(): Flow<List<Lobby>>

    @Query("SELECT * FROM Lobby WHERE createdBy = :uid")
    fun watchLobbiesCreatedBy(uid: String): Flow<List<Lobby>>

    @Query("SELECT * FROM Lobby WHERE :uid IN (participants)")
    fun watchLobbiesJoinedBy(uid: String): Flow<List<Lobby>>
}