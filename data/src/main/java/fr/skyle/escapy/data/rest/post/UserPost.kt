package fr.skyle.escapy.data.rest.post

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserPost(
    @SerialName("name") val name: String,
    @SerialName("email") val email: String?,
)