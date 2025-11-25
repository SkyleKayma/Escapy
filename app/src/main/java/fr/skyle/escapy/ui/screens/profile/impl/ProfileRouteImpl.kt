package fr.skyle.escapy.ui.screens.profile.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.navigateToDestinationAndPopUpTo
import fr.skyle.escapy.ext.navigateWithLifecycle
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Graph
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.profile.ui.ProfileRoute

fun NavGraphBuilder.profileRoute(
    navHostController: NavHostController,
) {
    composable<Route.Profile> {
        ProfileRoute(
            navigateBack = navHostController::popBackStackWithLifecycle,
            navigateLinkAccount = {
                // TODO
            },
            navigateChangeEmail = {
                navHostController.navigateWithLifecycle(Route.ChangeEmail)
            },
            navigateChangePassword = {
                navHostController.navigateWithLifecycle(Route.ChangePassword)
            },
            navigateToNotifications = {
                // TODO
            },
            navigateToAboutApp = {
                navHostController.navigateWithLifecycle(Route.About)
            },
            navigateToSignIn = {
                navHostController.navigateToDestinationAndPopUpTo<Graph.Main>(Route.SignIn())
            },
        )
    }
}