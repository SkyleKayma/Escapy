package fr.skyle.escapy.data.rest.firebase

import androidx.annotation.Keep

@Keep
data class UserRequestDTO(
    val username: String = "",
    val avatarType: Int? = null,
    val createdAt: Long? = null,
)