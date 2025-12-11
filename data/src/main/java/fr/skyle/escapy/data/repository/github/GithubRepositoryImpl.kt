package fr.skyle.escapy.data.repository.github

import fr.skyle.escapy.data.repository.github.api.GithubRepository
import fr.skyle.escapy.data.rest.github.GithubAPI
import fr.skyle.escapy.data.vo.GithubContributor
import fr.skyle.escapy.data.vo.mapper.toGithubContributor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    private val githubAPI: GithubAPI,
) : GithubRepository {

    override suspend fun getGithubContributors(): List<GithubContributor> {
        val githubContributors = githubAPI.getGithubContributors()
        return githubContributors?.mapNotNull { it.toGithubContributor() } ?: emptyList()
    }
}