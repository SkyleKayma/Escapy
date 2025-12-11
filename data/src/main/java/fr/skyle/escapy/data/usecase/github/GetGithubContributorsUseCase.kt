package fr.skyle.escapy.data.usecase.github

import fr.skyle.escapy.data.repository.github.api.GithubRepository
import fr.skyle.escapy.data.vo.GithubContributor
import javax.inject.Inject

interface GetGithubContributorsUseCase {

    suspend operator fun invoke(): List<GithubContributor>
}

class GetGithubContributorsUseCaseImpl @Inject constructor(
    private val githubRepository: GithubRepository
) : GetGithubContributorsUseCase {

    override suspend fun invoke(): List<GithubContributor> =
        githubRepository.getGithubContributors()
}