package fr.skyle.escapy.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey val uid: String,
    val name: String,
    val email: String? = null,
)
