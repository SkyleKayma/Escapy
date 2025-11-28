package fr.skyle.escapy.designsystem.core.bottomsheet

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Popup
import fr.skyle.escapy.designsystem.core.snackbar.ProjectSnackbar
import fr.skyle.escapy.designsystem.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    snackbarState: ProjectSnackbarState? = null,
    content: @Composable () -> Unit,
) {
    ModalBottomSheet(
        modifier = modifier.statusBarsPadding(),
        sheetState = sheetState,
        onDismissRequest = {
            snackbarState?.snackbarHostState?.currentSnackbarData?.dismiss()
            onDismissRequest()
        },
        shape = ProjectBottomSheetDefaults.bottomSheetShape,
        containerColor = ProjectTheme.colors.surfaceContainerLow,
    ) {
        content()
        snackbarState?.let {
            Popup(alignment = Alignment.BottomCenter) {
                ProjectSnackbar(
                    modifier = Modifier
                        .imePadding()
                        .fillMaxWidth()
                        .windowInsetsPadding(insets = BottomSheetDefaults.windowInsets),
                    state = snackbarState
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun ProjectBottomSheetPreview() {
    ProjectTheme {
        ProjectBottomSheet(
            sheetState = rememberModalBottomSheetState(),
            onDismissRequest = {}
        ) {
            Text(text = "Text")
        }
    }
}