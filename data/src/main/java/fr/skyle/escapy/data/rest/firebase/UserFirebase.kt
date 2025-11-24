package fr.skyle.escapy.data.rest.firebase

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserFirebase(
    @SerialName("name") val name: String = "",
    @SerialName("email") val email: String? = null,
    @SerialName("avatarType") val avatarType: Int? = null,
    @SerialName("createdAt") val createdAt: Long? = null,
)