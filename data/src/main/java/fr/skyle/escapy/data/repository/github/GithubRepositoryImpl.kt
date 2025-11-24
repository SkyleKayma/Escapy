package fr.skyle.escapy.data.repository.github

import fr.skyle.escapy.data.repository.github.api.GithubRepository
import fr.skyle.escapy.data.rest.GithubAPI
import fr.skyle.escapy.data.rest.response.GithubContributor
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(
    private val githubAPI: GithubAPI,
) : GithubRepository {

    override suspend fun getGithubContributors(): Result<List<GithubContributor>?> {
        val response = githubAPI.getGithubContributors()

        return if (response.isSuccessful) {
            val body = response.body()

            Result.success(body)
        } else {
            Result.failure(Exception(response.message()))
        }
    }
}