package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState

@Composable
fun HomeRoute(
    navigateToProfile: () -> Unit,
    navigateToCreateLobby: () -> Unit,
    navigateToLobby: (lobbyId: String) -> Unit,
    navigateToJoinLobbyByQRCode: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val homeState by homeViewModel.state.collectAsStateWithLifecycle()

    val snackbarState = rememberProjectSnackbarState()

    LaunchedEffect(homeState.event) {
        homeState.event?.let { event ->
            when (event) {
                is HomeViewModel.HomeEvent.FetchError -> {
                    snackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_fetch_format,
                            event.message
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }

            homeViewModel.eventDelivered()
        }
    }

    HomeScreen(
        snackbarState = snackbarState,
        state = homeState,
        onProfileClicked = navigateToProfile,
        onCreateLobby = navigateToCreateLobby,
        onRefresh = homeViewModel::fetch,
        onHomeActiveLobbyClicked = navigateToLobby,
        onJoinLobbyByQRCodeClicked = navigateToJoinLobbyByQRCode
    )
}