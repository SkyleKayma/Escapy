package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO

interface UserRemoteDataSource {

    suspend fun getUser(userId: String): UserRequestDTO?

    suspend fun updateAvatar(
        userId: String,
        avatar: Avatar
    )
}