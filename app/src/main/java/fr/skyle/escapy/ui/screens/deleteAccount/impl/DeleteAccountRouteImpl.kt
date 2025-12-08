package fr.skyle.escapy.ui.screens.deleteAccount.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.navigateToDestinationAndPopUpTo
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Graph
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.deleteAccount.ui.DeleteAccountRoute

fun NavGraphBuilder.deleteAccountRoute(
    navHostController: NavHostController,
) {
    composable<Route.DeleteAccount> {
        DeleteAccountRoute(
            navigateBackToSignIn = {
                navHostController.navigateToDestinationAndPopUpTo<Graph.Main>(
                    routeToNavigateTo = Route.SignIn(),
                    isInclusive = true
                )
            },
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}