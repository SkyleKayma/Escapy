package fr.skyle.escapy.ui.screens.home.ui.mapper

import fr.skyle.escapy.data.vo.Lobby
import fr.skyle.escapy.ui.screens.home.ui.model.LobbyUI

fun Lobby.toLobbyUI(): LobbyUI =
    LobbyUI(
        uid = uid,
        title = title,
        duration = duration,
        createdAt = createdAt,
        status = status,
        createdByName = createdByName,
        nbParticipants = participants.size
    )