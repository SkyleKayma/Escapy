package fr.skyle.escapy.ui.screens.deleteAccount.ui

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
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview

@Composable
fun DeleteAccountScreen(
    snackbarState: ProjectSnackbarState,
    state: DeleteAccountViewModel.State,
    password: String,
    onPasswordChange: (password: String) -> Unit,
    onDeleteAccountClicked: () -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.delete_account_title),
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
        DeleteAccountScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            authProvider = state.authProvider,
            isButtonLoading = state.isButtonLoading,
            password = password,
            onPasswordChange = onPasswordChange,
            onDeleteAccountClicked = onDeleteAccountClicked,
        )
    }
}

@Composable
private fun DeleteAccountScreenContent(
    innerPadding: PaddingValues,
    authProvider: AuthProvider,
    isButtonLoading: Boolean,
    password: String,
    onPasswordChange: (password: String) -> Unit,
    onDeleteAccountClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val shouldShowPasswordField = authProvider == AuthProvider.EMAIL

    Column(
        modifier = modifier
            .padding(top = innerPadding.calculateTopPadding())
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
            .imePadding()
    ) {
        if (shouldShowPasswordField) {
            ProjectTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = onPasswordChange,
                label = stringResource(id = R.string.delete_account_current_password),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onDeleteAccountClicked()
                        keyboardController?.hide()
                    }
                ),
                isEnabled = !isButtonLoading
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.delete_account_warning_delete_account),
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface
        )

        if (authProvider == AuthProvider.ANONYMOUS) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.delete_account_warning_delete_account_anonymous),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onDeleteAccountClicked()
                keyboardController?.hide()
            },
            text = stringResource(R.string.generic_action_delete),
            style = ProjectButtonDefaults.Style.FILLED,
            tint = ProjectButtonDefaults.Tint.PRIMARY,
            size = ProjectButtonDefaults.Size.LARGE,
            isLoading = isButtonLoading,
            isEnabled = if (shouldShowPasswordField) {
                password.isNotBlank()
            } else {
                true
            }
        )

        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun ChangeEmailScreenPreview() {
    ProjectTheme {
        DeleteAccountScreen(
            snackbarState = rememberProjectSnackbarState(),
            state = DeleteAccountViewModel.State(),
            password = "",
            onPasswordChange = {},
            onDeleteAccountClicked = {},
            navigateBack = {}
        )
    }
}

private class DeleteAccountScreenContentPreviewDataProvider :
    CollectionPreviewParameterProvider<DeleteAccountScreenContentPreviewData>(
        collection = buildList {
            AuthProvider.entries.forEach { authProvider ->
                Boolean.values.forEach { isButtonLoading ->
                    add(
                        DeleteAccountScreenContentPreviewData(
                            authProvider = authProvider,
                            isButtonLoading = isButtonLoading,
                        )
                    )
                }
            }
        }
    )

private data class DeleteAccountScreenContentPreviewData(
    val authProvider: AuthProvider,
    val isButtonLoading: Boolean,
)

@ProjectComponentPreview
@Composable
private fun DeleteAccountScreenContentPreview(
    @PreviewParameter(DeleteAccountScreenContentPreviewDataProvider::class) data: DeleteAccountScreenContentPreviewData
) {
    ProjectTheme {
        DeleteAccountScreenContent(
            innerPadding = PaddingValues(),
            authProvider = data.authProvider,
            isButtonLoading = data.isButtonLoading,
            password = "",
            onPasswordChange = {},
            onDeleteAccountClicked = {},
        )
    }
}