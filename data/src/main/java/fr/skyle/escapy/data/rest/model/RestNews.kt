package fr.skyle.escapy.data.rest.model

import kotlinx.serialization.Serializable

@Serializable
data class RestNews(
    val id: String? = null,
    val title: String? = null,
    val excerpt: String? = null,
    val content: String? = null,
    val created_at: Long? = null
)