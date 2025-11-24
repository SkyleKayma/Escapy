package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState

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
                        )
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
                        )
                    )
                }
            }

            signInViewModel.eventDelivered()
        }
    }

    SignInScreen(
        projectSnackbarState = projectSnackbarState,
        signInState = signInState,
        onSignInClicked = signInViewModel::signIn,
        onSignUpClicked = signInViewModel::signUp,
        onGoogleSignInClicked = signInViewModel::signInWithGoogle,
        onContinueAsGuestClicked = signInViewModel::signInAsGuest,
        onChangeAuthTypeClicked = signInViewModel::changeAuthType
    )
}