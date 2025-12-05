package fr.skyle.escapy.data.repository.lobby.model

data class CreateLobbyRequest(
    val title: String,
    val password: String?,
    val duration: Long,
    val createdBy: String
)