package fr.skyle.escapy.data.rest.github.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubContributorResponse(
    @SerialName("id") val id: Long,
    @SerialName("login") val username: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("html_url") val personalRepoUrl: String? = null,
    @SerialName("contributions") val nbContributions: Int? = null,
)