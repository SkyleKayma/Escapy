package fr.skyle.escapy.ui.screens.changePassword.ui

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
import fr.skyle.escapy.ui.screens.changePassword.ui.component.PasswordChangedDialog

@Composable
fun ChangePasswordRoute(
    navigateBack: () -> Unit,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val changePasswordState by changePasswordViewModel.changePasswordState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    var isPasswordChangedDialogDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(changePasswordState.event) {
        changePasswordState.event?.let { event ->
            when (event) {
                ChangePasswordViewModel.ChangePasswordEvent.Success -> {
                    isPasswordChangedDialogDisplayed = true
                }

                ChangePasswordViewModel.ChangePasswordEvent.InvalidFields -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_invalid_fields),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL
                    )
                }

                ChangePasswordViewModel.ChangePasswordEvent.InvalidCurrentPassword -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_invalid_current_password),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                is ChangePasswordViewModel.ChangePasswordEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }
        }

        changePasswordViewModel.eventDelivered()
    }

    ChangePasswordScreen(
        snackbarState = projectSnackbarState,
        changePasswordState = changePasswordState,
        onCurrentPasswordChange = changePasswordViewModel::setCurrentPassword,
        onNewPasswordChange = changePasswordViewModel::setNewPassword,
        onNewPasswordConfirmationChange = changePasswordViewModel::setNewPasswordConfirmation,
        onChangePassword = changePasswordViewModel::changePassword,
        navigateBack = navigateBack,
    )

    if (isPasswordChangedDialogDisplayed) {
        PasswordChangedDialog(
            onDismissRequest = {
                isPasswordChangedDialogDisplayed = false
                navigateBack()
            }
        )
    }
}