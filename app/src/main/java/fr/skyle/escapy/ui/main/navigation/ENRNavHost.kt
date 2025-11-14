package fr.skyle.escapy.ui.main.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import fr.skyle.escapy.ui.screens.home.impl.homeRoute
import fr.skyle.escapy.ui.screens.login.impl.loginRoute

@Composable
fun ProjectNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Graph.Auth,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween()
            )
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
        navigation<Graph.Auth>(
            startDestination = Route.Login
        ) {
            loginRoute(
                navHostController = navHostController
            )
        }

        navigation<Graph.Main>(
            startDestination = Route.Home
        ) {
            homeRoute(
                navHostController = navHostController
            )
        }
    }
}