package fr.skyle.escapy.data.rest

import fr.skyle.escapy.data.rest.model.RestNews
import retrofit2.http.GET
import retrofit2.http.Path

interface EscapyApi {

    @GET("app/applications/news")
    suspend fun getNews(): List<RestNews>

    @GET("app/applications/news/{newsId}")
    suspend fun getNewsWithId(
        @Path("newsId") newsId: String
    ): RestNews
}