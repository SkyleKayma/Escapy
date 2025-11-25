package fr.skyle.escapy.ui.main

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.navigateToDestinationAndPopUpTo
import fr.skyle.escapy.ui.main.navigation.Graph
import fr.skyle.escapy.ui.main.navigation.ProjectNavHost
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel()
) {
    val navHostController = rememberNavController()

    val mainState by mainViewModel.mainState.collectAsStateWithLifecycle()

    val isUserLoggedIn = remember {
        mainViewModel.isUserLoggedIn()
    }

    val startDestination = remember(isUserLoggedIn) {
        if (isUserLoggedIn) {
            Graph.Main
        } else {
            Graph.Auth
        }
    }

    LaunchedEffect(mainState.authenticatorEvent) {
        mainState.authenticatorEvent?.let { event ->
            when (event) {
                MainViewModel.AuthenticatorEvent.LogoutEvent -> {
                    navHostController.navigateToDestinationAndPopUpTo<Graph.Main>(
                        Route.SignIn(
                            reason = SignInReason.SESSION_EXPIRED
                        )
                    )
                }
            }

            mainViewModel.authenticatorEventDelivered()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = ProjectTheme.colors.surfaceContainerLow,
        contentWindowInsets = WindowInsets(0),
    ) { innerPadding ->
        ProjectNavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding),
            navHostController = navHostController,
            startDestination = startDestination
        )
    }
}