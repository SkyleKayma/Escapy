package fr.skyle.escapy.data.vo.adapter

import fr.skyle.escapy.data.rest.github.response.GithubContributorResponseDTO
import fr.skyle.escapy.data.vo.GithubContributor

fun GithubContributor.toGithubContributorResponse(): GithubContributorResponseDTO =
    GithubContributorResponseDTO(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        personalRepoUrl = personalRepoUrl,
        nbContributions = nbContributions
    )

fun GithubContributorResponseDTO.toGithubContributor(): GithubContributor? =
    username?.let {
        GithubContributor(
            id = id,
            username = it,
            avatarUrl = avatarUrl,
            personalRepoUrl = personalRepoUrl,
            nbContributions = nbContributions
        )
    }