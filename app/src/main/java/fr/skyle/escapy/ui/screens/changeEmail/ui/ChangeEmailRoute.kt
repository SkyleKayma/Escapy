package fr.skyle.escapy.ui.screens.changeEmail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.screens.changeEmail.ui.component.EmailVerificationSentDialog

@Composable
fun ChangeEmailRoute(
    navigateBack: () -> Unit,
    changeEmailViewModel: ChangeEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val changeEmailState by changeEmailViewModel.changeEmailState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    var isEmailVerificationSentDialogDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(changeEmailState.event) {
        changeEmailState.event?.let { event ->
            when (event) {
                ChangeEmailViewModel.ChangeEmailEvent.EmailVerificationSent -> {
                    isEmailVerificationSentDialogDisplayed = true
                }

                ChangeEmailViewModel.ChangeEmailEvent.InvalidFields -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_incorrect_fields),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL
                    )
                }

                is ChangeEmailViewModel.ChangeEmailEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }

            changeEmailViewModel.eventDelivered()
        }
    }

    ChangeEmailScreen(
        snackbarState = projectSnackbarState,
        changeEmailState = changeEmailState,
        onCurrentPasswordChange = changeEmailViewModel::setCurrentPassword,
        onNewEmailChanged = changeEmailViewModel::setNewEmail,
        onSaveProfile = changeEmailViewModel::saveProfile,
        navigateBack = navigateBack,
    )

    if (isEmailVerificationSentDialogDisplayed) {
        EmailVerificationSentDialog(
            onDismissRequest = {
                isEmailVerificationSentDialogDisplayed = false
                navigateBack()
            }
        )
    }
}