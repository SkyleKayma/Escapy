package fr.skyle.escapy.ui.screens.linkAccountWithEmail.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.linkAccountWithEmail.ui.LinkAccountWithEmailRoute

fun NavGraphBuilder.linkAccountWithEmailRoute(
    navHostController: NavHostController,
) {
    composable<Route.LinkAccountWithEmail> {
        LinkAccountWithEmailRoute(
            navigateBackToProfile = {
                navHostController.popBackStackWithLifecycle<Route.Profile>(inclusive = false)
            },
            navigateBack = navHostController::popBackStackWithLifecycle,
        )
    }
}