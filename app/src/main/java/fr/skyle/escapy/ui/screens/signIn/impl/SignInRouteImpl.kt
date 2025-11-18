package fr.skyle.escapy.ui.screens.signIn.impl

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.signIn.ui.SignInRoute

fun NavGraphBuilder.signInRoute(
    navHostController: NavHostController,
) {
    composable<Route.SignIn>(
        enterTransition = {
            fadeIn(animationSpec = tween())
        },
        exitTransition = {
            fadeOut(animationSpec = tween())
        }
    ) {
        SignInRoute(
            navigateToHome = {
                navHostController.navigate(Route.Home) {
                    popUpTo(Route.SignIn) {
                        inclusive = true
                    }
                }
            },
            navigateToSignUp = {

            }
        )
    }
}