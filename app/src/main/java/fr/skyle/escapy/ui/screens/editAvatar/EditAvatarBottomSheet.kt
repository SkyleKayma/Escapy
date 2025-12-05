package fr.skyle.escapy.ui.screens.editAvatar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.core.bottomsheet.ProjectBottomSheet
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.dialog.ProjectLoadingDialog
import fr.skyle.escapy.ui.screens.editAvatar.component.EditAvatarItem
import fr.skyle.escapy.utils.ProjectComponentPreview
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAvatarBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    editAvatarViewModel: EditAvatarViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val state by editAvatarViewModel.state.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    val animateToDismiss: () -> Unit = {
        scope.launch { sheetState.hide() }.invokeOnCompletion {
            if (!sheetState.isVisible) {
                onDismissRequest()
            }
        }
    }

    LaunchedEffect(state.event) {
        state.event?.let { event ->
            when (event) {
                is EditAvatarViewModel.EditAvatarEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(
                            R.string.generic_error_format,
                            event.message ?: "-"
                        ),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                EditAvatarViewModel.EditAvatarEvent.Success -> {
                    animateToDismiss()
                }
            }

            editAvatarViewModel.eventDelivered()
        }
    }

    val errorFormat = stringResource(R.string.generic_error_format)

    LaunchedEffect(state.event) {
        state.event?.let { event ->
            when (event) {
                is EditAvatarViewModel.EditAvatarEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = errorFormat.format(event.message ?: "-"),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                EditAvatarViewModel.EditAvatarEvent.Success -> {
                    animateToDismiss()
                }
            }

            editAvatarViewModel.eventDelivered()
        }
    }

    ProjectBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        snackbarState = projectSnackbarState,
        onDismissRequest = animateToDismiss,
    ) {
        EditAvatarBottomSheetContent(
            currentAvatar = state.currentAvatar,
            isAvatarUpdating = state.isAvatarUpdating,
            onAvatarClicked = editAvatarViewModel::updateAvatar,
        )
    }

    if (state.isLoadingShown) {
        ProjectLoadingDialog(
            title = stringResource(R.string.edit_avatar_loading_dialog_title),
        )
    }
}

@Composable
private fun EditAvatarBottomSheetContent(
    currentAvatar: Avatar?,
    isAvatarUpdating: Boolean,
    onAvatarClicked: (avatar: Avatar) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(R.string.edit_avatar_screen_title),
            style = ProjectTheme.typography.b1,
            color = ProjectTheme.colors.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyVerticalGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = GridCells.Fixed(4),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, bottom = 24.dp)
        ) {
            items(
                items = Avatar.entries,
                key = { it.type }
            ) { avatar ->
                EditAvatarItem(
                    avatar = avatar,
                    isSelected = avatar == currentAvatar,
                    isEnabled = !isAvatarUpdating,
                    onClick = {
                        onAvatarClicked(avatar)
                    }
                )
            }
        }
    }
}

private class EditAvatarBottomSheetContentPreviewDataProvider :
    CollectionPreviewParameterProvider<EditAvatarBottomSheetContentPreviewData>(
        collection = buildList {
            Boolean.values.forEach { isUpdatingAvatar ->
                add(
                    EditAvatarBottomSheetContentPreviewData(
                        isUpdatingAvatar = isUpdatingAvatar,
                    )
                )
            }
        }
    )

private data class EditAvatarBottomSheetContentPreviewData(
    val isUpdatingAvatar: Boolean,
)

@ProjectComponentPreview
@Composable
private fun EditAvatarBottomSheetContentPreview(
    @PreviewParameter(EditAvatarBottomSheetContentPreviewDataProvider::class) data: EditAvatarBottomSheetContentPreviewData
) {
    ProjectTheme {
        EditAvatarBottomSheetContent(
            currentAvatar = Avatar.AVATAR_01,
            isAvatarUpdating = data.isUpdatingAvatar,
            onAvatarClicked = {},
        )
    }
}