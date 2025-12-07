package fr.skyle.escapy.data.vo.junction

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import fr.skyle.escapy.data.vo.Lobby

@Entity(
    primaryKeys = ["lobbyId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = Lobby::class,
            parentColumns = ["uid"],
            childColumns = ["lobbyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["lobbyId"]),
        Index(value = ["userId"])
    ]
)
data class LobbyParticipantCrossRef(
    val lobbyId: String,
    val userId: String
)