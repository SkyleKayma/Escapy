package fr.skyle.escapy.data.vo.mapper

import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.vo.User

fun User.toUserRequestDTO(): UserRequestDTO =
    UserRequestDTO(
        username = username,
        avatarType = avatarType,
        createdAt = createdAt
    )

fun UserRequestDTO.toUser(uid: String): User =
    User(
        uid = uid,
        username = username ?: "",
        avatarType = avatarType,
        createdAt = createdAt
    )