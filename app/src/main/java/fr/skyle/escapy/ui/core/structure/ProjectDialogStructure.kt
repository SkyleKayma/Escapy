package fr.skyle.escapy.ui.core.structure

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.core.dialog.ProjectDialogDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProjectDialogStructure(
    title: String,
    leftButtonText: String,
    rightButtonText: String,
    onLeftButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    dialogButtonsOrientation: ProjectDialogDefaults.ButtonsOrientation = ProjectDialogDefaults.dialogButtonsOrientation
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = ProjectTheme.typography.b2,
            color = ProjectTheme.colors.onSurface,
            textAlign = TextAlign.Center,
        )

        subtitle?.let {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = subtitle,
                style = ProjectTheme.typography.p2,
                color = ProjectTheme.colors.onSurface,
                textAlign = TextAlign.Center,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (dialogButtonsOrientation) {
            ProjectDialogDefaults.ButtonsOrientation.HORIZONTAL -> {
                Row(modifier = Modifier.fillMaxWidth()) {
                    ProjectButton(
                        modifier = Modifier.weight(1f),
                        text = leftButtonText,
                        size = ProjectButtonDefaults.Size.MEDIUM,
                        tint = ProjectButtonDefaults.Tint.SECONDARY,
                        style = ProjectButtonDefaults.Style.FILLED,
                        onClick = onLeftButtonClick,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    ProjectButton(
                        modifier = Modifier.weight(1f),
                        text = rightButtonText,
                        size = ProjectButtonDefaults.Size.MEDIUM,
                        tint = ProjectButtonDefaults.Tint.PRIMARY,
                        style = ProjectButtonDefaults.Style.FILLED,
                        onClick = onRightButtonClick,
                    )
                }
            }

            ProjectDialogDefaults.ButtonsOrientation.VERTICAL -> {
                Column(modifier = Modifier.fillMaxWidth()) {
                    ProjectButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = leftButtonText,
                        size = ProjectButtonDefaults.Size.MEDIUM,
                        tint = ProjectButtonDefaults.Tint.SECONDARY,
                        style = ProjectButtonDefaults.Style.FILLED,
                        onClick = onLeftButtonClick,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ProjectButton(
                        modifier = Modifier.fillMaxWidth(),
                        text = rightButtonText,
                        size = ProjectButtonDefaults.Size.MEDIUM,
                        tint = ProjectButtonDefaults.Tint.PRIMARY,
                        style = ProjectButtonDefaults.Style.FILLED,
                        onClick = onRightButtonClick,
                    )
                }
            }
        }
    }
}

private class ProjectDialogStructurePreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectDialogStructurePreviewData>(
        collection = buildList {
            ProjectDialogDefaults.ButtonsOrientation.entries.forEach { buttonOrientation ->
                add(
                    ProjectDialogStructurePreviewData(
                        buttonOrientation = buttonOrientation,
                    )
                )
            }
        }
    )

private data class ProjectDialogStructurePreviewData(
    val buttonOrientation: ProjectDialogDefaults.ButtonsOrientation
)

@Composable
@ProjectComponentPreview
private fun ProjectDialogStructurePreview(
    @PreviewParameter(ProjectDialogStructurePreviewDataProvider::class) data: ProjectDialogStructurePreviewData
) {
    ProjectTheme {
        ProjectDialogStructure(
            title = "Title",
            subtitle = "Subtitle",
            leftButtonText = "Left Button",
            rightButtonText = "Right Button",
            onLeftButtonClick = {},
            onRightButtonClick = {},
            dialogButtonsOrientation = data.buttonOrientation
        )
    }
}