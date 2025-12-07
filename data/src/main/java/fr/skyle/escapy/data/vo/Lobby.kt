package fr.skyle.escapy.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.skyle.escapy.data.enums.LobbyStatus

@Entity
data class Lobby(
    @PrimaryKey val uid: String,
    val title: String,
    val password: String?,
    val duration: Long,
    val createdAt: Long,
    val startedAt: Long?,
    val endedAt: Long?,
    val status: LobbyStatus,
    val createdBy: String,
    val createdByName: String,
    val participants: List<String>
)