package fr.skyle.escapy.data.rest.github

import fr.skyle.escapy.data.rest.github.response.GithubContributorResponseDTO
import retrofit2.Response
import retrofit2.http.GET

interface GithubAPI {

    /**
     * Get contributors of the project
     */
    @GET("contributors")
    suspend fun getGithubContributors(): Response<List<GithubContributorResponseDTO>?>
}