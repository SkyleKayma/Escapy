package fr.skyle.escapy.ui.screens.bottomsheets.editAvatar

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.skyle.escapy.R
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.designsystem.core.bottomsheet.ProjectBottomSheet
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbarDefaults
import fr.skyle.escapy.designsystem.core.snackbar.state.rememberProjectSnackbarState
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.screens.bottomsheets.editAvatar.component.EditAvatarItem
import fr.skyle.escapy.utils.ProjectComponentPreview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAvatarBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    editAvatarViewModel: EditAvatarViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val state by editAvatarViewModel.editAvatarState.collectAsStateWithLifecycle()

    val projectSnackbarState = rememberProjectSnackbarState()

    LaunchedEffect(state.event) {
        state.event?.let { event ->
            when (event) {
                is EditAvatarViewModel.EditAvatarEvent.Error -> {
                    projectSnackbarState.showSnackbar(
                        message = context.getString(R.string.generic_error_format, event.message),
                        type = ProjectSnackbarDefaults.ProjectSnackbarType.ERROR
                    )
                }

                EditAvatarViewModel.EditAvatarEvent.Success -> {
                    onDismissRequest()
                }
            }

            editAvatarViewModel.eventDelivered()
        }
    }

    ProjectBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        snackbarState = projectSnackbarState,
        onDismissRequest = onDismissRequest,
    ) {
        EditAvatarBottomSheetContent(
            currentAvatar = state.currentAvatar,
            onAvatarClicked = editAvatarViewModel::updateAvatar,
        )
    }
}

@Composable
private fun EditAvatarBottomSheetContent(
    currentAvatar: Avatar?,
    onAvatarClicked: (avatar: Avatar) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(R.string.edit_avatar_title),
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
            items(Avatar.entries) { avatar ->
                EditAvatarItem(
                    avatar = avatar,
                    isSelected = avatar == currentAvatar,
                    onClick = {
                        onAvatarClicked(avatar)
                    }
                )
            }
        }
    }
}

@ProjectComponentPreview
@Composable
private fun EditAvatarBottomSheetContentPreview() {
    ProjectTheme {
        EditAvatarBottomSheetContent(
            currentAvatar = Avatar.AVATAR_01,
            onAvatarClicked = {},
        )
    }
}