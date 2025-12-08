package fr.skyle.escapy.ui.screens.joinLobbyByQRCode.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.navigateToDestinationAndPopUpTo
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.joinLobbyByQRCode.ui.JoinLobbyByQRCodeRoute

fun NavGraphBuilder.joinLobbyByQRCodeRoute(
    navHostController: NavHostController,
) {
    composable<Route.JoinLobbyByQRCode> {
        JoinLobbyByQRCodeRoute(
            navigateToLobby = {
                navHostController.navigateToDestinationAndPopUpTo<Route.Home>(Route.About)
            },
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}