package fr.skyle.escapy.data.rest.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequestDTO(
    @SerialName("username") val username: String = "",
    @SerialName("avatarType") val avatarType: Int? = null,
    @SerialName("createdAt") val createdAt: Long? = null,
)