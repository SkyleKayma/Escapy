package fr.skyle.escapy.data.ext

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import fr.skyle.escapy.data.enums.AuthProvider

val AuthProvider.providerId: String
    get() = when (this) {
        AuthProvider.EMAIL ->
            EmailAuthProvider.PROVIDER_ID

        AuthProvider.GOOGLE ->
            GoogleAuthProvider.PROVIDER_ID

        AuthProvider.ANONYMOUS ->
            "anonymous"
    }