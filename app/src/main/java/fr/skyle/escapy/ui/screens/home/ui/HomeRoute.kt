package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    navigateToProfile: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = homeState,
        onProfileClicked = navigateToProfile
    )
}