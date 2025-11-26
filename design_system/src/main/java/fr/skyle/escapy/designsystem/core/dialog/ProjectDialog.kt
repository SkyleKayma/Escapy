package fr.skyle.escapy.designsystem.core.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectDialog(
    onDismissRequest: () -> Unit,
    properties: DialogProperties = ProjectDialogDefaults.defaultDialogProperties,
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        ProjectDialogContent(
            content = content,
        )
    }
}

@Composable
private fun ProjectDialogContent(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(ProjectDialogDefaults.dialogShape)
            .background(color = ProjectTheme.colors.surfaceContainerLow)
            .padding(paddingValues = ProjectDialogDefaults.dialogPaddingValues),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
private fun ProjectDialogPreview() {
    ProjectTheme {
        ProjectDialog(
            onDismissRequest = {}
        ) {
            Text(
                text = "Content Content Content Content Content Content",
                style = ProjectTheme.typography.p2,
                color = ProjectTheme.colors.onSurface
            )
        }
    }
}