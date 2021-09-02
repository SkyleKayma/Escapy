package fr.skyle.escapy.data.rest.model

import fr.skyle.escapy.data.vo.News
import kotlinx.serialization.Serializable

@Serializable
data class RestNews(
    val id: String? = null,
    val title: String? = null,
    val excerpt: String? = null,
    val content: String? = null,
    val created_at: Long? = null
) {

    fun toNews(): News? =
        if (id != null && title != null && content != null && excerpt != null) {
            News(
                id,
                title,
                excerpt,
                content,
                created_at
            )
        } else null
}