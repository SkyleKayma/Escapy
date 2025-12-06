package fr.skyle.escapy.ui.screens.home.ui.model

import fr.skyle.escapy.data.enums.LobbyStatus

data class LobbyUI(
    val uid: String,
    val title: String,
    val duration: Long,
    val createdAt: Long,
    val status: LobbyStatus,
    val createdBy: String,
    val nbParticipants: Int
)