package fr.skyle.escapy.data.vo.mapper

import fr.skyle.escapy.data.enums.LobbyStatus
import fr.skyle.escapy.data.rest.firebase.LobbyRequestDTO
import fr.skyle.escapy.data.vo.Lobby

fun Lobby.toRequestDTO(): LobbyRequestDTO =
    LobbyRequestDTO(
        title = title,
        password = password,
        duration = duration,
        createdAt = createdAt,
        startedAt = startedAt,
        endedAt = endedAt,
        status = status.name,
        createdBy = createdBy,
        createdByName = createdByName,
        participants = participants.associateWith { true }
    )

fun LobbyRequestDTO.toLobby(id: String): Lobby =
    Lobby(
        uid = id,
        title = title ?: "",
        password = password ?: "",
        duration = duration ?: 0L,
        createdAt = createdAt ?: 0L,
        startedAt = startedAt,
        endedAt = endedAt,
        status = status?.let { LobbyStatus.valueOf(it) } ?: LobbyStatus.NOT_STARTED,
        createdBy = createdBy ?: "",
        createdByName = createdByName ?: "",
        participants = participants?.keys?.toList() ?: emptyList()
    )