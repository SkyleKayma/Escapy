package fr.skyle.escapy.data.repository.github

import fr.skyle.escapy.data.repository.github.api.GithubLocalDataSource
import fr.skyle.escapy.data.vo.GithubContributor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubLocalDataSourceImpl @Inject constructor(
    private val localDataSource: GithubLocalDataSource
) : GithubLocalDataSource {

    override suspend fun getGithubContributors(): List<GithubContributor> =
        localDataSource.getGithubContributors()
}