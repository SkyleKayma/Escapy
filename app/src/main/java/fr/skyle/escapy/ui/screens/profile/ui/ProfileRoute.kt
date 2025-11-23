package fr.skyle.escapy.ui.screens.profile.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun ProfileRoute(
    navigateBack: () -> Unit,
    navigateToSignIn: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.profileState.collectAsState()

    LaunchedEffect(profileState.event) {
        profileState.event?.let { event ->
            when (event) {
                ProfileViewModel.ProfileEvent.SignOutSuccess -> {
                    navigateToSignIn()
                }
            }

            profileViewModel.eventDelivered()
        }
    }

    ProfileScreen(
        profileState = profileState,
        onBackButtonClicked = navigateBack,
        onSignOutClicked = profileViewModel::signOut
    )
}