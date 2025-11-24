package fr.skyle.escapy.data.repository.github.api

import fr.skyle.escapy.data.rest.response.GithubContributor

interface GithubRepository {

    suspend fun getGithubContributors(): Result<List<GithubContributor>?>
}