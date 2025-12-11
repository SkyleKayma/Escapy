package fr.skyle.escapy.data.repository.github.api

import fr.skyle.escapy.data.vo.GithubContributor

interface GithubLocalDataSource {

    suspend fun getGithubContributors(): List<GithubContributor>
}