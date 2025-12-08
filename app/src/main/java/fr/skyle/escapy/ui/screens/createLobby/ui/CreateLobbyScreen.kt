package fr.skyle.escapy.ui.screens.createLobby.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.core.textField.ProjectClickableTextField
import fr.skyle.escapy.designsystem.core.textField.ProjectTextField
import fr.skyle.escapy.designsystem.core.topAppBar.ProjectTopAppBar
import fr.skyle.escapy.designsystem.core.topAppBar.component.ProjectTopAppBarItem
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ext.formatDuration
import fr.skyle.escapy.ui.core.structure.ProjectScreenStructure
import fr.skyle.escapy.utils.ProjectComponentPreview
import fr.skyle.escapy.utils.ProjectScreenPreview
import kotlin.time.Duration.Companion.hours

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLobbyScreen(
    snackbarState: ProjectSnackbarState,
    state: CreateLobbyViewModel.State,
    onTitleChange: (title: String) -> Unit,
    onShowTimePicker: () -> Unit,
    onCreateLobby: () -> Unit,
    navigateBack: () -> Unit,
) {
    ProjectScreenStructure(
        modifier = Modifier.fillMaxSize(),
        isPatternDisplayed = true,
        snackbarState = snackbarState,
        topContent = {
            ProjectTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.create_lobby_screen_title),
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
        CreateLobbyScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = innerPadding,
            isButtonLoading = state.isButtonLoading,
            title = state.title,
            duration = state.duration,
            titleValidationState = state.titleValidationState,
            durationValidationState = state.durationValidationState,
            onTitleChange = onTitleChange,
            onShowTimePicker = onShowTimePicker,
            onCreateLobby = onCreateLobby,
        )
    }
}

@Composable
private fun CreateLobbyScreenContent(
    innerPadding: PaddingValues,
    isButtonLoading: Boolean,
    title: String,
    duration: Long,
    titleValidationState: CreateLobbyViewModel.TitleValidationState,
    durationValidationState: CreateLobbyViewModel.DurationValidationState,
    onTitleChange: (currentPassword: String) -> Unit,
    onShowTimePicker: () -> Unit,
    onCreateLobby: () -> Unit,
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
            value = title,
            onValueChange = onTitleChange,
            label = stringResource(id = R.string.create_lobby_title),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            isEnabled = !isButtonLoading,
            isError = !titleValidationState.isValid && titleValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(4.dp))

        ProjectClickableTextField(
            modifier = Modifier.fillMaxWidth(),
            value = if (duration == 0L) {
                ""
            } else {
                formatDuration(duration)
            },
            onClick = {
                keyboardController?.hide()
                onShowTimePicker()
            },
            label = stringResource(id = R.string.create_lobby_max_duration),
            isEnabled = !isButtonLoading,
            isError = !durationValidationState.isValid && durationValidationState.hasBeenChecked
        )

        Spacer(modifier = Modifier.height(24.dp))

        ProjectButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onCreateLobby()
                keyboardController?.hide()
            },
            text = stringResource(R.string.create_lobby_create),
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
private fun CreateLobbyScreenPreview() {
    ProjectTheme {
        CreateLobbyScreen(
            snackbarState = rememberProjectSnackbarState(),
            state = CreateLobbyViewModel.State(),
            onTitleChange = {},
            onShowTimePicker = {},
            onCreateLobby = {},
            navigateBack = {},
        )
    }
}

private class CreateLobbyScreenContentPreviewDataProvider :
    CollectionPreviewParameterProvider<CreateLobbyScreenContentPreviewData>(
        collection = buildList {
            Boolean.values.forEach { isButtonLoading ->
                add(
                    CreateLobbyScreenContentPreviewData(
                        isButtonLoading = isButtonLoading,
                    )
                )
            }
        }
    )

private data class CreateLobbyScreenContentPreviewData(
    val isButtonLoading: Boolean,
)


@ProjectComponentPreview
@Composable
private fun CreateLobbyScreenContentPreview(
    @PreviewParameter(CreateLobbyScreenContentPreviewDataProvider::class) data: CreateLobbyScreenContentPreviewData
) {
    ProjectTheme {
        CreateLobbyScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            isButtonLoading = data.isButtonLoading,
            title = "",
            duration = 0L,
            titleValidationState = CreateLobbyViewModel.TitleValidationState(),
            durationValidationState = CreateLobbyViewModel.DurationValidationState(),
            onTitleChange = {},
            onShowTimePicker = {},
            onCreateLobby = {},
        )
    }
}

@ProjectComponentPreview
@Composable
private fun CreateLobbyScreenContentWithDurationPreview(
    @PreviewParameter(CreateLobbyScreenContentPreviewDataProvider::class) data: CreateLobbyScreenContentPreviewData
) {
    ProjectTheme {
        CreateLobbyScreenContent(
            modifier = Modifier.fillMaxSize(),
            innerPadding = PaddingValues(),
            isButtonLoading = data.isButtonLoading,
            title = "",
            duration = 1.hours.inWholeMilliseconds,
            titleValidationState = CreateLobbyViewModel.TitleValidationState(),
            durationValidationState = CreateLobbyViewModel.DurationValidationState(),
            onTitleChange = {},
            onShowTimePicker = {},
            onCreateLobby = {},
        )
    }
}