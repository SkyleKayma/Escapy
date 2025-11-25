package fr.skyle.escapy.ui.screens.changePassword.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState

@Composable
fun ChangePasswordRoute(
    navigateBack: () -> Unit,
    changePasswordViewModel: ChangePasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val changePasswordState by changePasswordViewModel.changePasswordState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    LaunchedEffect(changePasswordState.event) {
        changePasswordState.event?.let { event ->
            when (event) {
                is ChangePasswordViewModel.ChangePasswordEvent.Error -> {
                    when (val error = event.error) {
                        is ChangePasswordViewModel.ChangePasswordError.Error -> {
                            projectSnackbarState.showSnackbar(
                                message = context.getString(
                                    R.string.generic_error_format,
                                    error.errorMessage ?: "-"
                                ),
                                type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                            )
                        }

                        ChangePasswordViewModel.ChangePasswordError.InvalidFields -> {
                            projectSnackbarState.showSnackbar(
                                message = context.getString(R.string.generic_error_incorrect_fields),
                                type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                            )
                        }
                    }
                }

                ChangePasswordViewModel.ChangePasswordEvent.Success -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.change_password_success),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS
                    )
                }
            }

            changePasswordViewModel.eventDelivered()
        }
    }

    ChangePasswordScreen(
        projectSnackbarState = projectSnackbarState,
        changePasswordState = changePasswordState,
        onBackButtonClicked = navigateBack,
        onCurrentPasswordChange = changePasswordViewModel::setCurrentPassword,
        onNewPasswordChange = changePasswordViewModel::setNewPassword,
        onNewPasswordConfirmationChange = changePasswordViewModel::setNewPasswordConfirmation,
        onChangePassword = changePasswordViewModel::changePassword
    )
}