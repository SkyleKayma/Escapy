package fr.skyle.escapy.ui.screens.changeEmail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState

@Composable
fun ChangeEmailRoute(
    navigateBack: () -> Unit,
    changeEmailViewModel: ChangeEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val changeEmailState by changeEmailViewModel.changeEmailState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    LaunchedEffect(changeEmailState.event) {
        changeEmailState.event?.let { event ->
            when (event) {
                is ChangeEmailViewModel.ChangeEmailEvent.Error -> {
                    when (val error = event.error) {
                        is ChangeEmailViewModel.ChangeEmailError.Error -> {
                            projectSnackbarState.showSnackbar(
                                message = context.getString(
                                    R.string.generic_error_format,
                                    error.errorMessage ?: "-"
                                )
                            )
                        }

                        ChangeEmailViewModel.ChangeEmailError.InvalidFields -> {
                            projectSnackbarState.showSnackbar(
                                message = context.getString(R.string.generic_error_incorrect_fields)
                            )
                        }
                    }
                }

                ChangeEmailViewModel.ChangeEmailEvent.Success -> {
                    navigateBack()
                }
            }

            changeEmailViewModel.eventDelivered()
        }
    }

    ChangeEmailScreen(
        projectSnackbarState = projectSnackbarState,
        changeEmailState = changeEmailState,
        onBackButtonClicked = navigateBack,
        onCurrentPasswordChanged = changeEmailViewModel::setCurrentPassword,
        onNewEmailChanged = changeEmailViewModel::setNewEmail,
        onSaveProfile = changeEmailViewModel::saveProfile,
    )
}