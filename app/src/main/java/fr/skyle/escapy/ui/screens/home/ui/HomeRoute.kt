package fr.skyle.escapy.ui.screens.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun HomeRoute(
    navigateToProfile: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState by homeViewModel.homeState.collectAsState()

    HomeScreen(
        homeState = homeState,
        onProfileClicked = navigateToProfile
    )
}