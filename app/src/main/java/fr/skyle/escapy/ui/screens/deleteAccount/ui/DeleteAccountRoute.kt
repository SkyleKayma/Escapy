package fr.skyle.escapy.ui.screens.deleteAccount.ui

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
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.screens.deleteAccount.ui.component.DeleteAccountConfirmationDialog

@Composable
fun DeleteAccountRoute(
    navigateBackToSignIn: () -> Unit,
    navigateBack: () -> Unit,
    deleteAccountViewModel: DeleteAccountViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val deleteAccountState by deleteAccountViewModel.state.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    var password by remember {
        mutableStateOf("")
    }

    var isDeleteAccountConfirmationDialogDisplayed by remember { mutableStateOf(false) }

    LaunchedEffect(deleteAccountState.event) {
        deleteAccountState.event?.let { event ->
            when (event) {
                DeleteAccountViewModel.DeleteAccountEvent.Success -> {
                    navigateBackToSignIn()
                }

                DeleteAccountViewModel.DeleteAccountEvent.InvalidCurrentPassword -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_invalid_current_password),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                is DeleteAccountViewModel.DeleteAccountEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }

            deleteAccountViewModel.eventDelivered()
        }
    }

    DeleteAccountScreen(
        snackbarState = projectSnackbarState,
        state = deleteAccountState,
        password = password,
        onPasswordChange = {
            password = it
        },
        onDeleteAccountClicked = {
            isDeleteAccountConfirmationDialogDisplayed = true
        },
        navigateBack = navigateBack,
    )

    if (isDeleteAccountConfirmationDialogDisplayed) {
        DeleteAccountConfirmationDialog(
            onDeleteAccountClicked = {
                when (deleteAccountState.authProvider) {
                    AuthProvider.EMAIL -> {
                        deleteAccountViewModel.deleteAccountFromEmailProvider(password)
                    }

                    AuthProvider.GOOGLE -> {
                        // TODO
                    }

                    AuthProvider.ANONYMOUS -> {
                        deleteAccountViewModel.deleteAccountFromAnonymousProvider()
                    }
                }

                isDeleteAccountConfirmationDialogDisplayed = false
            },
            onDismissRequest = {
                isDeleteAccountConfirmationDialogDisplayed = false
            }
        )
    }
}