package fr.skyle.escapy.ui.screens.lobby.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.lobby.ui.LobbyRoute

fun NavGraphBuilder.lobbyRoute(
    navHostController: NavHostController,
) {
    composable<Route.LobbyRoute> {
        LobbyRoute(
            navigateBack = navHostController::popBackStackWithLifecycle,
        )
    }
}