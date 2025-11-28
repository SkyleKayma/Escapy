package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.screens.signIn.ui.ext.messageId

@Composable
fun SignInRoute(
    navigateToHome: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val signInState by signInViewModel.signInState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    LaunchedEffect(signInState.event) {
        signInState.event?.let { event ->
            when (event) {
                is SignInViewModel.SignInEvent.SignInError -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.errorMessage ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                SignInViewModel.SignInEvent.SignInSuccess,
                SignInViewModel.SignInEvent.SignUpSuccess -> {
                    navigateToHome()
                }

                is SignInViewModel.SignInEvent.SignUpError -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.errorMessage ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }

            signInViewModel.eventDelivered()
        }
    }

    LaunchedEffect(signInState.signInReasonEvent) {
        signInState.signInReasonEvent?.let { event ->
            when (event) {
                is SignInViewModel.SignInReasonEvent.FromReason -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(event.signInReason.messageId),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL
                    )
                }
            }

            signInViewModel.signInReasonEventDelivered()
        }
    }

    SignInScreen(
        snackbarState = projectSnackbarState,
        signInState = signInState,
        onSignInClicked = signInViewModel::signIn,
        onSignUpClicked = signInViewModel::signUp,
        onGoogleSignInClicked = signInViewModel::signInWithGoogle,
        onContinueAsGuestClicked = signInViewModel::signInAsGuest,
        onChangeAuthTypeClicked = signInViewModel::changeAuthType
    )
}