package fr.skyle.escapy.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider

val AuthProvider.displayText: String
    @Composable
    get() = when (this) {
        AuthProvider.EMAIL ->
            stringResource(R.string.auth_provider_email)

        AuthProvider.GOOGLE ->
            stringResource(R.string.auth_provider_google)

        AuthProvider.ANONYMOUS ->
            stringResource(R.string.auth_provider_anonymous)
    }