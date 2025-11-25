package fr.skyle.escapy.ui.screens.changeEmail.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.changeEmail.ui.ChangeEmailRoute

fun NavGraphBuilder.changeEmailRoute(
    navHostController: NavHostController,
) {
    composable<Route.ChangeEmail> {
        ChangeEmailRoute(
            navigateBack = navHostController::popBackStackWithLifecycle
        )
    }
}