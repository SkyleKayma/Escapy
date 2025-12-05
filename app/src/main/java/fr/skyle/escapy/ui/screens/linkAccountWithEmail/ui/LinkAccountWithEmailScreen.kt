package fr.skyle.escapy.ui.screens.linkAccountWithEmail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.form.PasswordFormValidation
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun LinkAccountWithEmailScreen(
    snackbarState: ProjectSnackbarState,
    state: LinkAccountWithEmailViewModel.State,
    onEmailChange: (email: String) -> Unit,
    onPasswordChange: (password: String) -> Unit,
    onPasswordConfirmationChange: (passwordConfirmation: String) -> Unit,
    onLinkAccount: () -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.link_account_with_email_screen_title),
                leadingContent = {
                    ProjectTopAppBarItem(
                        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                        style = ProjectIconButtonDefaults.Style.FILLED,
                        onClick = navigateBack,
                    )
                },
            )
        }
    ) { innerPadding ->
        LinkAccountWithEmailScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isButtonLoading = state.isButtonLoading,
            email = state.email,
            password = state.password,
            passwordConfirmation = state.passwordConfirmation,
            emailValidationState = state.emailValidationState,
            passwordValidationState = state.passwordValidationState,
            passwordConfirmationValidationState = state.passwordConfirmationValidationState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onPasswordConfirmationChange = onPasswordConfirmationChange,
            onLinkAccount = onLinkAccount,
        )
    }
}

@Composable
private fun LinkAccountWithEmailScreenContent(
    innerPadding: PaddingValues,
    isButtonLoading: Boolean,
    email: String,
    password: String,
    passwordConfirmation: String,
    emailValidationState: LinkAccountWithEmailViewModel.EmailValidationState,
    passwordValidationState: LinkAccountWithEmailViewModel.PasswordValidationState,
    passwordConfirmationValidationState: LinkAccountWithEmailViewModel.PasswordConfirmationValidationState,
    onEmailChange: (email: String) -> Unit,
    onPasswordChange: (password: String) -> Unit,
    onPasswordConfirmationChange: (passwordConfirmation: String) -> Unit,
    onLinkAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .imePadding(),
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.link_account_with_email_please_fill_account_information),
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface,
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(id = R.string.change_email_new_email),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            isEnabled = !isButtonLoading,
            isError = !emailValidationState.isValid && emailValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(4.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(id = R.string.link_account_with_email_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            isEnabled = !isButtonLoading,
            isError = !passwordValidationState.isValid && passwordValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordFormValidation(
            modifier = Modifier.fillMaxWidth(),
            containsUppercase = passwordValidationState.containsUppercase,
            isLongEnough = passwordValidationState.isLongEnough,
            containsDigit = passwordValidationState.containsDigit,
            containsSpecialCharacter = passwordValidationState.containsSpecialCharacter,
        )

        Spacer(modifier = Modifier.height(4.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordConfirmation,
            onValueChange = onPasswordConfirmationChange,
            label = stringResource(id = R.string.link_account_with_email_password_confirmation),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onLinkAccount()
                    keyboardController?.hide()
                }
            ),
            isEnabled = !isButtonLoading,
            isError = !passwordConfirmationValidationState.isValid && passwordConfirmationValidationState.hasBeenChecked,
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onLinkAccount()
                keyboardController?.hide()
            },
            text = stringResource(R.string.generic_action_modify),
            style = ProjectButtonDefaults.Style.FILLED,
            tint = ProjectButtonDefaults.Tint.PRIMARY,
            size = ProjectButtonDefaults.Size.LARGE,
            isLoading = isButtonLoading,
        )

        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun LinkAccountWithEmailScreenPreview() {
    ProjectTheme {
        LinkAccountWithEmailScreen(
            snackbarState = rememberProjectSnackbarState(),
            state = LinkAccountWithEmailViewModel.State(),
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmationChange = {},
            onLinkAccount = {},
            navigateBack = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun LinkAccountScreenContentPreview() {
    ProjectTheme {
        LinkAccountWithEmailScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = false,
            email = "",
            password = "",
            passwordConfirmation = "",
            emailValidationState = LinkAccountWithEmailViewModel.EmailValidationState(),
            passwordValidationState = LinkAccountWithEmailViewModel.PasswordValidationState(),
            passwordConfirmationValidationState = LinkAccountWithEmailViewModel.PasswordConfirmationValidationState(),
            onEmailChange = {},
            onPasswordChange = {},
            onPasswordConfirmationChange = {},
            onLinkAccount = {},
        )
    }
}