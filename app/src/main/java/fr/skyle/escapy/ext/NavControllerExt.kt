package fr.skyle.escapy.ext

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.Navigator

fun NavBackStackEntry?.isLifecycleResumed(): Boolean =
    this?.lifecycle?.currentState == Lifecycle.State.RESUMED

fun NavController.popBackStackWithLifecycle() {
    if (currentBackStackEntry.isLifecycleResumed()) {
        popBackStack()
    }
}

inline fun <reified T : Any> NavController.popBackStackWithLifecycle(
    inclusive: Boolean,
    saveState: Boolean = false,
) {
    if (currentBackStackEntry.isLifecycleResumed()) {
        popBackStack<T>(
            inclusive = inclusive,
            saveState = saveState
        )
    }
}

fun NavController.navigateWithLifecycle(
    route: Any,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null,
) {
    if (currentBackStackEntry.isLifecycleResumed()) {
        navigate(
            route = route,
            navOptions = navOptions,
            navigatorExtras = navigatorExtras
        )
    }
}

fun NavController.navigateWithLifecycle(
    route: Any,
    builder: NavOptionsBuilder.() -> Unit
) {
    if (currentBackStackEntry.isLifecycleResumed()) {
        navigate(
            route = route,
            builder = builder
        )
    }
}

inline fun <reified R : Any> NavController.navigateToDestinationAndPopUpTo(
    routeToNavigateTo: Any,
    isInclusive: Boolean = true
) {
    navigate(routeToNavigateTo) {
        popUpTo<R> {
            inclusive = isInclusive
        }
    }
}