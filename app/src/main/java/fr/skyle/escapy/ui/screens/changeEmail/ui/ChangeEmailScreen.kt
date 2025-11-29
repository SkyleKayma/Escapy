package fr.skyle.escapy.ui.screens.changeEmail.ui

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
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun ChangeEmailScreen(
    snackbarState: ProjectSnackbarState,
    changeEmailState: ChangeEmailViewModel.ChangeEmailState,
    onCurrentPasswordChange: (currentPassword: String) -> Unit,
    onNewEmailChanged: (newEmail: String) -> Unit,
    onSaveProfile: () -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.change_email_title),
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
        ChangeEmailScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isButtonLoading = changeEmailState.isButtonLoading,
            newEmail = changeEmailState.newEmail,
            currentPassword = changeEmailState.currentPassword,
            newEmailValidationState = changeEmailState.newEmailValidationState,
            currentPasswordValidationState = changeEmailState.currentPasswordValidationState,
            onNewEmailChange = onNewEmailChanged,
            onCurrentPasswordChange = onCurrentPasswordChange,
            onChangeEmail = onSaveProfile,
        )
    }
}

@Composable
private fun ChangeEmailScreenContent(
    innerPadding: PaddingValues,
    isButtonLoading: Boolean,
    newEmail: String,
    currentPassword: String,
    newEmailValidationState: ChangeEmailViewModel.NewEmailValidationState,
    currentPasswordValidationState: ChangeEmailViewModel.CurrentPasswordValidationState,
    onNewEmailChange: (newEmail: String) -> Unit,
    onCurrentPasswordChange: (currentPassword: String) -> Unit,
    onChangeEmail: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .imePadding()
    ) {
        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newEmail,
            onValueChange = onNewEmailChange,
            label = stringResource(id = R.string.change_email_new_email),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            isEnabled = !isButtonLoading,
            isError = !newEmailValidationState.isValid && newEmailValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = currentPassword,
            onValueChange = onCurrentPasswordChange,
            label = stringResource(id = R.string.change_email_current_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onChangeEmail()
                    keyboardController?.hide()
                }
            ),
            isEnabled = !isButtonLoading,
            isError = !currentPasswordValidationState.isValid && currentPasswordValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onChangeEmail()
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
private fun ChangeEmailScreenPreview() {
    ProjectTheme {
        ChangeEmailScreen(
            snackbarState = rememberProjectSnackbarState(),
            changeEmailState = ChangeEmailViewModel.ChangeEmailState(),
            onNewEmailChanged = {},
            onCurrentPasswordChange = {},
            onSaveProfile = {},
            navigateBack = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ChangeEmailScreenContentPreview() {
    ProjectTheme {
        ChangeEmailScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = false,
            newEmail = "",
            currentPassword = "",
            newEmailValidationState = ChangeEmailViewModel.NewEmailValidationState(),
            currentPasswordValidationState = ChangeEmailViewModel.CurrentPasswordValidationState(),
            onNewEmailChange = {},
            onCurrentPasswordChange = {},
            onChangeEmail = {}
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ChangeEmailScreenContentLoadingPreview() {
    ProjectTheme {
        ChangeEmailScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = true,
            newEmail = "",
            currentPassword = "",
            newEmailValidationState = ChangeEmailViewModel.NewEmailValidationState(),
            currentPasswordValidationState = ChangeEmailViewModel.CurrentPasswordValidationState(),
            onNewEmailChange = {},
            onCurrentPasswordChange = {},
            onChangeEmail = {}
        )
    }
}