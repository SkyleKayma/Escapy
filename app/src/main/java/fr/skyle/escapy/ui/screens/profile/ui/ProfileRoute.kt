package fr.skyle.escapy.ui.screens.profile.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.ui.screens.editAvatar.EditAvatarBottomSheet
import fr.skyle.escapy.ui.screens.profile.ui.component.SignOutConfirmationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileRoute(
    navigateLinkAccount: () -> Unit,
    navigateChangeEmail: () -> Unit,
    navigateChangePassword: () -> Unit,
    navigateToNotifications: () -> Unit,
    navigateToAboutApp: () -> Unit,
    navigateBackToSignIn: () -> Unit,
    navigateToDeleteAccount: () -> Unit,
    navigateBack: () -> Unit,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val profileState by profileViewModel.state.collectAsStateWithLifecycle()

    val editAvatarBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isEditAvatarBottomSheetDisplayed by remember {
        mutableStateOf(false)
    }
    var isSignOutConfirmationDialogDisplayed by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(profileState.event) {
        profileState.event?.let { event ->
            when (event) {
                ProfileViewModel.ProfileEvent.SignOutSuccess -> {
                    navigateBackToSignIn()
                }
            }

            profileViewModel.eventDelivered()
        }
    }

    ProfileScreen(
        state = profileState,
        navigateBack = navigateBack,
        onEditAvatarClicked = {
            isEditAvatarBottomSheetDisplayed = true
        },
        onLinkAccountClicked = navigateLinkAccount,
        onChangeEmailClicked = navigateChangeEmail,
        onChangePasswordClicked = navigateChangePassword,
        onNotificationsClicked = navigateToNotifications,
        onAboutAppClicked = navigateToAboutApp,
        onSignOutClicked = {
            isSignOutConfirmationDialogDisplayed = true
        },
        onDeleteAccountClicked = navigateToDeleteAccount,
    )

    if (isEditAvatarBottomSheetDisplayed) {
        EditAvatarBottomSheet(
            sheetState = editAvatarBottomSheetState,
            onDismissRequest = {
                isEditAvatarBottomSheetDisplayed = false
            }
        )
    }

    if (isSignOutConfirmationDialogDisplayed) {
        SignOutConfirmationDialog(
            onSignOutClicked = {
                profileViewModel.signOut()
                isSignOutConfirmationDialogDisplayed = false
            },
            onDismissRequest = {
                isSignOutConfirmationDialogDisplayed = false
            }
        )
    }
}