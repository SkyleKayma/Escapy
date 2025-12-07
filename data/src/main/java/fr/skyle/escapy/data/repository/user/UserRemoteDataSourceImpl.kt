package fr.skyle.escapy.data.repository.user

import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.FirebaseNode
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRemoteDataSource
import fr.skyle.escapy.data.rest.firebase.UserRequestDTO
import fr.skyle.escapy.data.utils.model.FirebaseResponse
import fr.skyle.escapy.data.utils.readOnce
import fr.skyle.escapy.data.utils.updateOnce
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSourceImpl @Inject constructor(
    private val dbRef: DatabaseReference,
) : UserRemoteDataSource {

    override suspend fun getUser(userId: String): FirebaseResponse<UserRequestDTO> =
        dbRef
            .child(FirebaseNode.USERS)
            .child(userId)
            .readOnce(UserRequestDTO::class.java)

    override suspend fun updateAvatar(userId: String, avatar: Avatar): FirebaseResponse<Unit> {
        val update = mapOf(
            UserRequestDTO::avatarType.name to avatar.type
        )

        return dbRef
            .child(FirebaseNode.USERS)
            .child(userId)
            .updateOnce(update)
    }
}