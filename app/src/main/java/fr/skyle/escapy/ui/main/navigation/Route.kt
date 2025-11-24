package fr.skyle.escapy.ui.main.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {

    @Serializable
    data object SignIn : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Profile : Route

    @Serializable
    data object About : Route

    @Serializable
    data object ChangePassword : Route
}

@Serializable
sealed interface Graph : Route {

    @Serializable
    data object Auth : Graph

    @Serializable
    data object Main : Graph
}