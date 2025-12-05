package fr.skyle.escapy.ui.main.navigation

import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data class SignIn(val reason: SignInReason? = null) : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object About : Route

    @Serializable
    data object ChangePassword : Route

    @Serializable
    data object ChangeEmail : Route

    @Serializable
    data object DeleteAccount : Route

    @Serializable
    data object LinkAccount : Route

    @Serializable
    data object LinkAccountWithEmail : Route

    @Serializable
    data object CreateLobby : Route
}

@Serializable
sealed interface Graph : Route {

    @Serializable
    data object Auth : Graph

    @Serializable
    data object Main : Graph
}