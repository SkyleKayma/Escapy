package fr.skyle.escapy.data.repository.user.api

import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.model.FirebaseResponse

interface UserRemoteDataSource {

    suspend fun fetchUser(userId: String): FirebaseResponse<UserRequestDTO>

    suspend fun updateAvatar(userId: String, avatar: Avatar): FirebaseResponse<Unit>
}