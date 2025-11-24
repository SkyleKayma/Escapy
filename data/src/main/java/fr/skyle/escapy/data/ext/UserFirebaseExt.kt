package fr.skyle.escapy.data.ext

import fr.skyle.escapy.data.rest.firebase.UserFirebase
import fr.skyle.escapy.data.vo.User

fun User.toUserFirebase(): UserFirebase =
    UserFirebase(
        name = name,
        email = email,
        avatarType = avatarType,
        createdAt = createdAt
    )

fun UserFirebase.toUser(uid: String): User =
    User(
        uid = uid,
        name = name,
        email = email,
        avatarType = avatarType,
        createdAt = createdAt
    )