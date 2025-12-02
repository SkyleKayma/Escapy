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
            navigateLinkAccount = {
                navHostController.navigateWithLifecycle(Route.LinkAccount)
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
            navigateBackToSignIn = {
                navHostController.navigateToDestinationAndPopUpTo<Graph.Main>(Route.SignIn())
            },
            navigateToDeleteAccount = {
                navHostController.navigateWithLifecycle(Route.DeleteAccount)
            },
            navigateBack = navHostController::popBackStackWithLifecycle,
        )
    }
}