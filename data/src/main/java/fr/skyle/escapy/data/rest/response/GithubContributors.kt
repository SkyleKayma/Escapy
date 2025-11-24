package fr.skyle.escapy.data.rest.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubContributor(
    @SerialName("id") val id: Long,
    @SerialName("login") val login: String? = null,
    @SerialName("avatar_url") val avatarUrl: String? = null,
    @SerialName("html_url") val personalRepoUrl: String? = null,
    @SerialName("contributions") val contributions: Int? = null,
)
