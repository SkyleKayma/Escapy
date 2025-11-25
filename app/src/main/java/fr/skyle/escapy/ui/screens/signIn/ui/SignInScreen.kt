package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.ui.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType
import fr.skyle.escapy.ui.screens.signIn.ui.ext.actionText
import fr.skyle.escapy.ui.screens.signIn.ui.ext.otherAuthTypeText
import fr.skyle.escapy.ui.screens.signIn.ui.ext.otherAuthTypeTextHighlighted
import fr.skyle.escapy.ui.screens.signIn.ui.ext.title
import fr.skyle.escapy.utils.AnnotatedData
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview
import fr.skyle.escapy.utils.buildAnnotatedString

@Composable
fun SignInScreen(
    projectSnackbarState: ProjectSnackbarState,
    signInState: SignInViewModel.SignInState,
    onSignInClicked: (String, String) -> Unit,
    onSignUpClicked: (String, String) -> Unit,
    onGoogleSignInClicked: () -> Unit,
    onContinueAsGuestClicked: () -> Unit,
    onChangeAuthTypeClicked: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = projectSnackbarState
    ) { innerPadding ->
        SignInScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            authType = signInState.authType,
            isButtonLoading = signInState.isButtonLoading,
            onSignInClicked = onSignInClicked,
            onSignUpClicked = onSignUpClicked,
            onGoogleSignInClicked = onGoogleSignInClicked,
            onContinueAsGuestClicked = onContinueAsGuestClicked,
            onChangeAuthTypeClicked = onChangeAuthTypeClicked
        )
    }
}

@Composable
private fun SignInScreenContent(
    innerPadding: PaddingValues,
    authType: AuthType,
    isButtonLoading: Boolean,
    onSignInClicked: (String, String) -> Unit,
    onSignUpClicked: (String, String) -> Unit,
    onGoogleSignInClicked: () -> Unit,
    onContinueAsGuestClicked: () -> Unit,
    onChangeAuthTypeClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.padding(innerPadding.calculateTopPadding()))

        Spacer(modifier = Modifier.weight(1f))

        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = R.drawable.ic_logo),
            contentDescription = null
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier.wrapContentWidth(),
            text = authType.title,
            style = ProjectTheme.typography.h1,
            color = ProjectTheme.colors.onSurface
        )

        Text(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.sign_in_up_subtitle),
            style = ProjectTheme.typography.h2,
            color = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(48.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.sign_in_up_email),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            isEnabled = !isButtonLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        ProjectTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = stringResource(id = R.string.sign_in_up_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    when (authType) {
                        AuthType.SIGN_IN ->
                            onSignInClicked(email, password)

                        AuthType.SIGN_UP ->
                            onSignUpClicked(email, password)
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
                when (authType) {
                    AuthType.SIGN_IN ->
                        onSignInClicked(email, password)

                    AuthType.SIGN_UP ->
                        onSignUpClicked(email, password)
                }
                keyboardController?.hide()
            },
            text = authType.actionText,
            style = ProjectButtonDefaults.ButtonStyle.FILLED,
            tint = ProjectButtonDefaults.ButtonTint.PRIMARY,
            size = ProjectButtonDefaults.ButtonSize.LARGE,
            isLoading = isButtonLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = ProjectTheme.colors.onSurface
            )

            Text(
                modifier = Modifier.wrapContentWidth(),
                text = stringResource(R.string.sign_in_up_or_sign_in_with),
                style = ProjectTheme.typography.p3,
                color = ProjectTheme.colors.onSurface
            )

            HorizontalDivider(
                modifier = Modifier.weight(1f),
                color = ProjectTheme.colors.onSurface
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProjectIconButton(
                icon = painterResource(R.drawable.ic_google),
                style = ProjectIconButtonDefaults.IconButtonStyle.FILLED,
                tint = ProjectIconButtonDefaults.IconButtonTint.DARK,
                onClick = onGoogleSignInClicked,
                isEnabled = !isButtonLoading
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        val accountText = buildAnnotatedString(
            fullText = authType.otherAuthTypeText,
            AnnotatedData(
                text = authType.otherAuthTypeTextHighlighted,
                spanStyle = SpanStyle(
                    color = ProjectTheme.colors.primary,
                    textDecoration = TextDecoration.Underline
                ),
                onClick = if (!isButtonLoading) {
                    onChangeAuthTypeClicked
                } else null
            )
        )

        Text(
            modifier = Modifier.wrapContentWidth(),
            text = accountText,
            style = ProjectTheme.typography.p2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        ProjectButton(
            modifier = Modifier.wrapContentWidth(),
            text = stringResource(R.string.sign_in_up_continue_as_guest),
            onClick = onContinueAsGuestClicked,
            style = ProjectButtonDefaults.ButtonStyle.OUTLINED,
            tint = ProjectButtonDefaults.ButtonTint.SECONDARY,
            size = ProjectButtonDefaults.ButtonSize.SMALL,
            isEnabled = !isButtonLoading
        )

        Spacer(modifier = Modifier.padding(innerPadding.calculateBottomPadding()))
    }
}

@ProjectScreenPreview
@Composable
private fun SignInScreenPreview() {
    ProjectTheme {
        SignInScreen(
            projectSnackbarState = rememberProjectSnackbarState(),
            signInState = SignInViewModel.SignInState(),
            onSignInClicked = { _, _ -> },
            onSignUpClicked = { _, _ -> },
            onGoogleSignInClicked = {},
            onContinueAsGuestClicked = {},
            onChangeAuthTypeClicked = {}
        )
    }
}

private class SignInScreenContentPreviewDataProvider :
    CollectionPreviewParameterProvider<SignInScreenContentPreviewData>(
        collection = buildList {
            AuthType.entries.forEach { authType ->
                Boolean.values.forEach { isButtonLoading ->
                    add(
                        SignInScreenContentPreviewData(
                            authType = authType,
                            isButtonLoading = isButtonLoading,
                        )
                    )
                }
            }
        }
    )

private data class SignInScreenContentPreviewData(
    val authType: AuthType,
    val isButtonLoading: Boolean,
)

@ProjectComponentPreview
@Composable
private fun SignInScreenContentPreview(
    @PreviewParameter(SignInScreenContentPreviewDataProvider::class) data: SignInScreenContentPreviewData

) {
    ProjectTheme {
        SignInScreenContent(
            innerPadding = PaddingValues(),
            authType = data.authType,
            isButtonLoading = data.isButtonLoading,
            onSignInClicked = { _, _ -> },
            onSignUpClicked = { _, _ -> },
            onGoogleSignInClicked = {},
            onContinueAsGuestClicked = {},
            onChangeAuthTypeClicked = {},
        )
    }
}