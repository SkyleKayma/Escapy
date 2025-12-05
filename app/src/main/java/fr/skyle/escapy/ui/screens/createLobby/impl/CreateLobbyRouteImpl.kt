package fr.skyle.escapy.ui.screens.createLobby.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.createLobby.ui.CreateLobbyRoute

fun NavGraphBuilder.createLobbyRoute(
    navHostController: NavHostController,
) {
    composable<Route.CreateLobby> {
        CreateLobbyRoute(
            navigateToLobby = {
                // TODO
            },
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}