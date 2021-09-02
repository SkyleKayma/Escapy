package fr.skyle.escapy.data.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class News(
    @PrimaryKey val id: String,
    val title: String,
    val excerpt: String,
    val content: String,
    val createdAt: Long? = null
)