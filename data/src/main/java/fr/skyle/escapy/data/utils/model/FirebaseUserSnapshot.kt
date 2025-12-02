package fr.skyle.escapy.data.utils.model

import fr.skyle.escapy.data.enums.AuthProvider

data class FirebaseUserSnapshot(
    val uid: String,
    val email: String?,
    val isEmailVerified: Boolean,
    val authProvider: AuthProvider?,
)