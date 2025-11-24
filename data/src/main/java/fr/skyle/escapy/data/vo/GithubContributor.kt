package fr.skyle.escapy.data.vo

data class GithubContributor(
    val id: Long,
    val username: String,
    val avatarUrl: String? = null,
    val personalRepoUrl: String? = null,
    val nbContributions: Int? = null,
)
