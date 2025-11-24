package fr.skyle.escapy.ui.screens.changePassword.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
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
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.change_password_error_format,
                            event.errorMessage ?: "-"
                        )
                    )
                }

                ChangePasswordViewModel.ChangePasswordEvent.Success -> {
                    navigateBack()
                }
            }

            changePasswordViewModel.eventDelivered()
        }
    }

    ChangePasswordScreen(
        projectSnackbarState = projectSnackbarState,
        changePasswordState = changePasswordState,
        onBackButtonClicked = navigateBack,
        onChangePassword = changePasswordViewModel::changePassword
    )
}