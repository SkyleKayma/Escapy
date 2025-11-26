package fr.skyle.escapy.ui.screens.changePassword.ui

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
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.form.PasswordFormValidationRow
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun ChangePasswordScreen(
    projectSnackbarState: ProjectSnackbarState,
    changePasswordState: ChangePasswordViewModel.ChangePasswordState,
    onBackButtonClicked: () -> Unit,
    onCurrentPasswordChange: (currentPassword: String) -> Unit,
    onNewPasswordChange: (newPassword: String) -> Unit,
    onNewPasswordConfirmationChange: (newPasswordConfirmation: String) -> Unit,
    onChangePassword: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = projectSnackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.change_password_title),
                leadingContent = {
                    ProjectTopAppBarItem(
                        icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
                        style = ProjectIconButtonDefaults.IconButtonStyle.FILLED,
                        onClick = onBackButtonClicked,
                    )
                },
            )
        }
    ) { innerPadding ->
        ChangePasswordScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isButtonLoading = changePasswordState.isButtonLoading,
            currentPassword = changePasswordState.currentPassword,
            newPassword = changePasswordState.newPassword,
            newPasswordConfirmation = changePasswordState.newPasswordConfirmation,
            currentPasswordValidationState = changePasswordState.currentPasswordValidationState,
            newPasswordValidationState = changePasswordState.newPasswordValidationState,
            newPasswordConfirmationValidationState = changePasswordState.newPasswordConfirmationValidationState,
            onCurrentPasswordChange = onCurrentPasswordChange,
            onNewPasswordChange = onNewPasswordChange,
            onNewPasswordConfirmationChange = onNewPasswordConfirmationChange,
            onChangePassword = onChangePassword
        )
    }
}

@Composable
private fun ChangePasswordScreenContent(
    innerPadding: PaddingValues,
    isButtonLoading: Boolean,
    currentPassword: String,
    newPassword: String,
    newPasswordConfirmation: String,
    currentPasswordValidationState: ChangePasswordViewModel.CurrentPasswordValidationState,
    newPasswordValidationState: ChangePasswordViewModel.NewPasswordValidationState,
    newPasswordConfirmationValidationState: ChangePasswordViewModel.NewPasswordConfirmationValidationState,
    onCurrentPasswordChange: (currentPassword: String) -> Unit,
    onNewPasswordChange: (newPassword: String) -> Unit,
    onNewPasswordConfirmationChange: (newPasswordConfirmation: String) -> Unit,
    onChangePassword: () -> Unit,
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
            value = currentPassword,
            onValueChange = onCurrentPasswordChange,
            label = stringResource(id = R.string.change_password_current_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            isEnabled = !isButtonLoading,
            isError = !currentPasswordValidationState.isValid && currentPasswordValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPassword,
            onValueChange = onNewPasswordChange,
            label = stringResource(id = R.string.change_password_new_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            isEnabled = !isButtonLoading,
            isError = !newPasswordValidationState.isValid && newPasswordValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = newPasswordValidationState.containsUppercase,
            text = stringResource(id = R.string.generic_password_rule_uppercase)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = newPasswordValidationState.isLongEnough,
            text = stringResource(id = R.string.generic_password_rule_min_length)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = newPasswordValidationState.containsDigit,
            text = stringResource(id = R.string.generic_password_rule_digit)
        )

        Spacer(modifier = Modifier.height(4.dp))

        PasswordFormValidationRow(
            modifier = Modifier.fillMaxWidth(),
            isValid = newPasswordValidationState.containsSpecialCharacter,
            text = stringResource(id = R.string.generic_password_rule_special)
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPasswordConfirmation,
            onValueChange = onNewPasswordConfirmationChange,
            label = stringResource(id = R.string.change_password_new_password_confirmation),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onChangePassword()
                    keyboardController?.hide()
                }
            ),
            isEnabled = !isButtonLoading,
            isError = !newPasswordConfirmationValidationState.isValid && newPasswordConfirmationValidationState.hasBeenChecked,
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onChangePassword()
                keyboardController?.hide()
            },
            text = stringResource(R.string.generic_action_modify),
            style = ProjectButtonDefaults.ButtonStyle.FILLED,
            tint = ProjectButtonDefaults.ButtonTint.PRIMARY,
            size = ProjectButtonDefaults.ButtonSize.LARGE,
            isLoading = isButtonLoading,
        )

        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun ChangePasswordScreenPreview() {
    ProjectTheme {
        ChangePasswordScreen(
            projectSnackbarState = rememberProjectSnackbarState(),
            changePasswordState = ChangePasswordViewModel.ChangePasswordState(),
            onBackButtonClicked = {},
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onNewPasswordConfirmationChange = {},
            onChangePassword = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ChangePasswordScreenContentPreview() {
    ProjectTheme {
        ChangePasswordScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = false,
            currentPassword = "",
            newPassword = "",
            newPasswordConfirmation = "",
            currentPasswordValidationState = ChangePasswordViewModel.CurrentPasswordValidationState(),
            newPasswordValidationState = ChangePasswordViewModel.NewPasswordValidationState(),
            newPasswordConfirmationValidationState = ChangePasswordViewModel.NewPasswordConfirmationValidationState(),
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onNewPasswordConfirmationChange = {},
            onChangePassword = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ChangePasswordScreenContentLoadingPreview() {
    ProjectTheme {
        ChangePasswordScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = true,
            currentPassword = "",
            newPassword = "",
            newPasswordConfirmation = "",
            currentPasswordValidationState = ChangePasswordViewModel.CurrentPasswordValidationState(),
            newPasswordValidationState = ChangePasswordViewModel.NewPasswordValidationState(),
            newPasswordConfirmationValidationState = ChangePasswordViewModel.NewPasswordConfirmationValidationState(),
            onCurrentPasswordChange = {},
            onNewPasswordChange = {},
            onNewPasswordConfirmationChange = {},
            onChangePassword = {},
        )
    }
}