package fr.skyle.escapy.ui.main.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navigation
import fr.skyle.escapy.ui.screens.about.impl.aboutRoute
import fr.skyle.escapy.ui.screens.changeEmail.impl.changeEmailRoute
import fr.skyle.escapy.ui.screens.changePassword.impl.changePasswordRoute
import fr.skyle.escapy.ui.screens.home.impl.homeRoute
import fr.skyle.escapy.ui.screens.profile.impl.profileRoute
import fr.skyle.escapy.ui.screens.signIn.impl.signInRoute

@Composable
fun ProjectNavHost(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: Route,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = startDestination,
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
            startDestination = Route.SignIn()
        ) {
            signInRoute(
                navHostController = navHostController
            )
        }

        navigation<Graph.Main>(
            startDestination = Route.Home
        ) {
            homeRoute(
                navHostController = navHostController
            )

            profileRoute(
                navHostController = navHostController
            )

            aboutRoute(
                navHostController = navHostController
            )

            changePasswordRoute(
                navHostController = navHostController
            )

            changeEmailRoute(
                navHostController = navHostController
            )
        }
    }
}