package fr.skyle.escapy.ui.screens.home.impl

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ext.navigateWithLifecycle
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.home.ui.HomeRoute

fun NavGraphBuilder.homeRoute(
    navHostController: NavHostController,
) {
    composable<Route.Home>(
        enterTransition = {
            if (initialState.destination.hasRoute<Route.SignIn>()) {
                fadeIn(animationSpec = tween())
            } else {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween()
                )
            }
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween()
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween()
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween()
            )
        }
    ) {
        HomeRoute(
            navigateToProfile = {
                navHostController.navigateWithLifecycle(Route.Profile)
            },
            navigateToCreateLobby = {
                navHostController.navigateWithLifecycle(Route.CreateLobby)
            },
            navigateToLobby = { lobbyId ->
                // TODO
            }
        )
    }
}