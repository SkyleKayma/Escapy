package fr.skyle.escapy.ui.screens.linkAccountWithEmail.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState

@Composable
fun LinkAccountWithEmailRoute(
    navigateBackToProfile: () -> Unit,
    navigateBack: () -> Unit,
    linkAccountWithEmailViewModel: LinkAccountWithEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val linkAccountWithEmailState by linkAccountWithEmailViewModel.state.collectAsStateWithLifecycle()

    val snackbarState = rememberProjectSnackbarState()

    LaunchedEffect(linkAccountWithEmailState.event) {
        linkAccountWithEmailState.event?.let { event ->
            when (event) {
                LinkAccountWithEmailViewModel.LinkAccountWithEmailEvent.Success -> {
                    navigateBackToProfile()
                }

                LinkAccountWithEmailViewModel.LinkAccountWithEmailEvent.InvalidFields -> {
                    snackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_invalid_fields),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.NEUTRAL
                    )
                }

                LinkAccountWithEmailViewModel.LinkAccountWithEmailEvent.EmailAlreadyUsed -> {
                    snackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_email_already_used),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                is LinkAccountWithEmailViewModel.LinkAccountWithEmailEvent.Error -> {
                    snackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }
            }

            linkAccountWithEmailViewModel.eventDelivered()
        }
    }

    LinkAccountWithEmailScreen(
        snackbarState = snackbarState,
        state = linkAccountWithEmailState,
        onEmailChange = linkAccountWithEmailViewModel::setEmail,
        onPasswordChange = linkAccountWithEmailViewModel::setPassword,
        onPasswordConfirmationChange = linkAccountWithEmailViewModel::setPasswordConfirmation,
        onLinkAccount = linkAccountWithEmailViewModel::linkAccountWithEmailProvider,
        navigateBack = navigateBack,
    )
}