package fr.skyle.escapy.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.skyle.escapy.data.enums.Avatar

@Entity
data class User(
    @PrimaryKey val uid: String,
    val username: String,
    val avatarType: Int? = null,
    val createdAt: Long? = null,
) {
    companion object {
        fun createDefault(userId: String, userNamePrefix: String): User =
            User(
                uid = userId,
                username = "${userNamePrefix}_${userId.takeLast(10)}",
                avatarType = Avatar.entries.random().type,
                createdAt = System.currentTimeMillis()
            )
    }
}