package fr.skyle.escapy.ui.screens.home.impl

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.home.ui.HomeRoute

fun NavGraphBuilder.homeRoute(
    navHostController: NavHostController,
) {
    composable<Route.Home>(
        enterTransition = {
            fadeIn(animationSpec = tween())
        },
        exitTransition = {
            fadeOut(animationSpec = tween())
        }
    ) {
        HomeRoute()
    }
}