package fr.skyle.escapy.data.rest

import fr.skyle.escapy.data.rest.response.GithubContributorResponse
import retrofit2.Response
import retrofit2.http.GET

interface GithubAPI {

    /**
     * Get contributors of the project
     */
    @GET("contributors")
    suspend fun getGithubContributors(): Response<List<GithubContributorResponse>?>
}