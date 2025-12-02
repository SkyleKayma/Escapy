package fr.skyle.escapy.ui.screens.linkAccount.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.navigateWithLifecycle
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.linkAccount.ui.LinkAccountRoute

fun NavGraphBuilder.linkAccountRoute(
    navHostController: NavHostController,
) {
    composable<Route.LinkAccount> {
        LinkAccountRoute(
            navigateToLinkAccountWithEmail = {
                navHostController.navigateWithLifecycle(Route.LinkAccountWithEmail)
            },
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}