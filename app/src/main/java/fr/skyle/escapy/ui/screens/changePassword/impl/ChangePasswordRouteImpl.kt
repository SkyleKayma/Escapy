package fr.skyle.escapy.ui.screens.changePassword.impl

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.popBackStackWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.changePassword.ui.ChangePasswordRoute

fun NavGraphBuilder.changePasswordRoute(
    navHostController: NavHostController,
) {
    composable<Route.ChangePassword> {
        ChangePasswordRoute(
            navigateBack = navHostController::popBackStackWithLifecycle,
        )
    }
}