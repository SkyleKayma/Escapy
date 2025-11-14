package fr.skyle.escapy.ui.screens.login.impl

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.login.ui.LoginRoute

fun NavGraphBuilder.loginRoute(
    navHostController: NavHostController,
) {
    composable<Route.Login>(
        enterTransition = {
            fadeIn(animationSpec = tween())
        },
        exitTransition = {
            fadeOut(animationSpec = tween())
        }
    ) {
        LoginRoute()
    }
}