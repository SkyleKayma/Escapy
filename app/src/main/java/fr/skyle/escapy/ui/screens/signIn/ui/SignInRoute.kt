package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import fr.skyle.escapy.R
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState

@Composable
fun SignInRoute(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit,
    signInViewModel: SignInViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val signInState by signInViewModel.signInState.collectAsState()

    val projectSnackbarState = rememberProjectSnackbarState()

    LaunchedEffect(signInState.event) {
        signInState.event?.let { event ->
            when (event) {
                is SignInViewModel.SignInEvent.SignInError -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.sign_in_error, event.errorMessage)
                    )
                }

                SignInViewModel.SignInEvent.SignInSuccess,
                SignInViewModel.SignInEvent.SignUpSuccess -> {
                    navigateToHome()
                }

                is SignInViewModel.SignInEvent.SignUpError -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.sign_up_error, event.errorMessage)
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
        onSignUpClicked = signInViewModel::signIn,
        onGoogleSignInClicked = signInViewModel::signInWithGoogle,
        onContinueAsGuestClicked = signInViewModel::signInAsGuest,
        onChangeAuthTypeClicked = signInViewModel::changeAuthType
    )
}