package fr.skyle.escapy.ui.screens.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ProfileRoute(
    navigateBack: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()

    ProfileScreen(
        profileState = profileState,
        onBackButtonClicked = navigateBack
    )
}