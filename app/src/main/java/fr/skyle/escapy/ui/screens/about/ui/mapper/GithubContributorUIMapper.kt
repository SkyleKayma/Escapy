package fr.skyle.escapy.ui.screens.about.ui.mapper

import fr.skyle.escapy.data.vo.GithubContributor
import fr.skyle.escapy.ui.screens.about.ui.model.GithubContributorUI

fun GithubContributor.toGithubContributorUI(): GithubContributorUI =
    GithubContributorUI(
        id = id,
        username = username,
        avatarUrl = avatarUrl,
        personalRepoUrl = personalRepoUrl
    )