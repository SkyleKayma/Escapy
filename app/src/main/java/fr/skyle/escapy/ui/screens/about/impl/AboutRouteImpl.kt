package fr.skyle.escapy.ui.screens.about.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.about.ui.AboutRoute

fun NavGraphBuilder.aboutRoute(
    navHostController: NavHostController,
) {
    composable<Route.About> {
        AboutRoute(
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}