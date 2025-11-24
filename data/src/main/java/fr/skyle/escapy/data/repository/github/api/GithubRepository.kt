package fr.skyle.escapy.data.repository.github.api

import fr.skyle.escapy.data.vo.GithubContributor

interface GithubRepository {

    suspend fun getGithubContributors(): Result<List<GithubContributor>>
}