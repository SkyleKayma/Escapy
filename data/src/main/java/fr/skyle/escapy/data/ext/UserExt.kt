package fr.skyle.escapy.data.ext

import fr.skyle.escapy.data.rest.post.UserPost
import fr.skyle.escapy.data.vo.User

fun User.toUserPost(): UserPost =
    UserPost(
        name = name,
        email = email,
    )