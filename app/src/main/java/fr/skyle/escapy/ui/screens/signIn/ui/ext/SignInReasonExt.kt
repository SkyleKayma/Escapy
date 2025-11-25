package fr.skyle.escapy.ui.screens.signIn.ui.ext

import androidx.annotation.StringRes
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason

val SignInReason.messageId: Int
    @StringRes
    get() = when (this) {
        SignInReason.SESSION_EXPIRED ->
            R.string.sign_in_error_session_expired
    }