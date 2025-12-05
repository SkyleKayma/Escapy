package fr.skyle.escapy.ui.screens.about.ui.model

data class GithubContributorUI(
    val id: Long,
    val username: String,
    val avatarUrl: String? = null,
    val personalRepoUrl: String? = null,
)