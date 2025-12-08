package fr.skyle.escapy.ui.core.dialog

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialog
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.structure.ProjectLoadingDialogStructure
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProjectLoadingDialog(
    title: String,
    subtitle: String? = null
) {
    ProjectDialog(
        onDismissRequest = {},
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true,
        ),
        content = {
            ProjectLoadingDialogStructure(
                title = title,
                subtitle = subtitle,
            )
        }
    )
}

@Composable
@ProjectComponentPreview
private fun ProjectLoadingDialogPreview() {
    ProjectTheme {
        ProjectLoadingDialog(
            title = stringResource(R.string.generic_loading_modifying),
        )
    }
}