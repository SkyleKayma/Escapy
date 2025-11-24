package fr.skyle.escapy.data.repository.github.api

import fr.skyle.escapy.data.rest.response.GithubContributorResponse

interface GithubRepository {

    suspend fun getGithubContributors(): Result<List<GithubContributorResponse>?>
}