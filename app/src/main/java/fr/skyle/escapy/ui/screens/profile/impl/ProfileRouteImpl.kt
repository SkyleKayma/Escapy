package fr.skyle.escapy.ui.screens.profile.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.profile.ui.ProfileRoute

fun NavGraphBuilder.profileRoute(
    navHostController: NavHostController,
) {
    composable<Route.Profile> {
        ProfileRoute(
            navigateBack = navHostController::popBackStack
        )
    }
}