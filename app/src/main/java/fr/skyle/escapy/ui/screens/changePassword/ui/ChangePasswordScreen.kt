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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun ChangePasswordScreen(
    projectSnackbarState: ProjectSnackbarState,
    changePasswordState: ChangePasswordViewModel.ChangePasswordState,
    onBackButtonClicked: () -> Unit,
    onChangePassword: (currentPassword: String, newPassword: String) -> Unit,
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
            onChangePassword = onChangePassword
        )
    }
}

@Composable
private fun ChangePasswordScreenContent(
    innerPadding: PaddingValues,
    isButtonLoading: Boolean,
    onChangePassword: (currentPassword: String, newPassword: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var newPasswordConfirmation by remember { mutableStateOf("") }

    val isButtonEnabled by remember {
        derivedStateOf {
            currentPassword.isNotBlank()
                    && newPassword.isNotBlank()
                    && newPassword == newPasswordConfirmation
        }
    }

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
            onValueChange = { currentPassword = it },
            label = stringResource(id = R.string.change_password_current_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            isEnabled = !isButtonLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPassword,
            onValueChange = { newPassword = it },
            label = stringResource(id = R.string.change_password_new_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            isEnabled = !isButtonLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newPasswordConfirmation,
            onValueChange = { newPasswordConfirmation = it },
            label = stringResource(id = R.string.change_password_new_password_confirmation),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isButtonEnabled) {
                        onChangePassword(currentPassword, newPassword)
                    }

                    keyboardController?.hide()
                }
            ),
            isEnabled = !isButtonLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onChangePassword(currentPassword, newPassword)
                keyboardController?.hide()
            },
            text = stringResource(R.string.change_password_modify),
            style = ProjectButtonDefaults.ButtonStyle.FILLED,
            tint = ProjectButtonDefaults.ButtonTint.PRIMARY,
            size = ProjectButtonDefaults.ButtonSize.LARGE,
            isLoading = isButtonLoading,
            isEnabled = isButtonEnabled
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
            onChangePassword = { _, _ -> },
        )
    }
}

@Preview
@Composable
private fun ChangePasswordScreenContentPreview() {
    ProjectTheme {
        ChangePasswordScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = false,
            onChangePassword = { _, _ -> }
        )
    }
}

@Preview
@Composable
private fun ChangePasswordScreenContentLoadingPreview() {
    ProjectTheme {
        ChangePasswordScreenContent(
            innerPadding = PaddingValues(),
            isButtonLoading = true,
            onChangePassword = { _, _ -> }
        )
    }
}