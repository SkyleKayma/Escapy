package fr.skyle.escapy.data.vo.adapter

import fr.skyle.escapy.data.rest.github.response.GithubContributorResponse
import fr.skyle.escapy.data.vo.GithubContributor

fun GithubContributor.toGithubContributorResponse(): GithubContributorResponse =
    GithubContributorResponse(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        personalRepoUrl = personalRepoUrl,
        nbContributions = nbContributions
    )

fun GithubContributorResponse.toGithubContributor(): GithubContributor? =
    username?.let {
        GithubContributor(
            id = id,
            username = it,
            avatarUrl = avatarUrl,
            personalRepoUrl = personalRepoUrl,
            nbContributions = nbContributions
        )
    }