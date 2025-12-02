package fr.skyle.escapy.ext

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider

val AuthProvider.icon: Painter
    @Composable
    get() = when (this) {
        AuthProvider.EMAIL ->
            rememberVectorPainter(Icons.Default.Password)

        AuthProvider.GOOGLE ->
            painterResource(R.drawable.ic_google)

        AuthProvider.ANONYMOUS ->
            rememberVectorPainter(Icons.Default.QuestionMark)
    }

val AuthProvider.displayTextShort: String
    @Composable
    get() = when (this) {
        AuthProvider.EMAIL ->
            stringResource(R.string.auth_provider_email_short)

        AuthProvider.GOOGLE ->
            stringResource(R.string.auth_provider_google_short)

        AuthProvider.ANONYMOUS ->
            stringResource(R.string.auth_provider_anonymous_short)
    }

val AuthProvider.displayTextLong: String
    @Composable
    get() = when (this) {
        AuthProvider.EMAIL ->
            stringResource(R.string.auth_provider_email_long)

        AuthProvider.GOOGLE ->
            stringResource(R.string.auth_provider_google_long)

        AuthProvider.ANONYMOUS ->
            stringResource(R.string.auth_provider_anonymous_long)
    }