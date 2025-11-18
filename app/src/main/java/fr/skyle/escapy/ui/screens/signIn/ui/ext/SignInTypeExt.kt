package fr.skyle.escapy.ui.screens.signIn.ui.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType

val AuthType.title: String
    @Composable
    get() = when (this) {
        AuthType.SIGN_IN ->
            stringResource(R.string.sign_in_title)

        AuthType.SIGN_UP ->
            stringResource(R.string.sign_up_title)
    }

val AuthType.actionText: String
    @Composable
    get() = when (this) {
        AuthType.SIGN_IN ->
            stringResource(R.string.sign_in_action)

        AuthType.SIGN_UP ->
            stringResource(R.string.sign_up_action)
    }

val AuthType.otherAuthTypeText: String
    @Composable
    get() = when (this) {
        AuthType.SIGN_IN ->
            stringResource(R.string.sign_in_no_account)

        AuthType.SIGN_UP ->
            stringResource(R.string.sign_in_have_account)
    }

val AuthType.otherAuthTypeTextHighlighted: String
    @Composable
    get() = when (this) {
        AuthType.SIGN_IN ->
            stringResource(R.string.sign_in_no_account_highlighted)

        AuthType.SIGN_UP ->
            stringResource(R.string.sign_in_have_account_highlighted)
    }