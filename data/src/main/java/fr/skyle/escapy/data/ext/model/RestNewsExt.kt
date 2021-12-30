package fr.skyle.escapy.data.ext.model

import fr.skyle.escapy.data.rest.model.RestNews
import fr.skyle.escapy.data.vo.News

fun RestNews.toNews(): News? =
    if (id != null && title != null && content != null && excerpt != null) {
        News(
            id,
            title,
            excerpt,
            content,
            created_at
        )
    } else null