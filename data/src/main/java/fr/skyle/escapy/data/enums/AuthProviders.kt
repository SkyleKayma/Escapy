package fr.skyle.escapy.data.enums

import fr.skyle.escapy.data.ext.providerId

enum class AuthProvider {
    EMAIL,
    GOOGLE,
    ANONYMOUS;

    companion object {
        fun fromProviderId(providerId: String?): AuthProvider? =
            entries.find { it.providerId == providerId }
    }
}