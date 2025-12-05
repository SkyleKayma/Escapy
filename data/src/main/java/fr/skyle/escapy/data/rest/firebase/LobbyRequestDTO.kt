package fr.skyle.escapy.data.rest.firebase

import androidx.annotation.Keep

@Keep
data class LobbyRequestDTO(
    var lobbyTitle: String? = null,
    var password: String? = null,
    var duration: Long? = null,
    var createdAt: Long? = null,
    var startedAt: Long? = null,
    var endedAt: Long? = null,
    var status: String? = null,
    var createdBy: String? = null,
    var participants: Map<String, Boolean>? = null
)