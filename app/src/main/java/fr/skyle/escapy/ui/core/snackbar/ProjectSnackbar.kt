package fr.skyle.escapy.ui.core.snackbar

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.button.ProjectButton
import fr.skyle.escapy.designsystem.core.button.ProjectButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.ui.core.snackbar.ext.iconColor
import fr.skyle.escapy.ui.core.snackbar.ext.painter
import fr.skyle.escapy.ui.core.snackbar.state.ProjectSnackbarState
import fr.skyle.escapy.utils.ProjectComponentPreview

@Composable
fun ProjectSnackbar(
    state: ProjectSnackbarState,
    modifier: Modifier = Modifier
) {
    SnackbarHost(
        modifier = modifier,
        hostState = state.snackbarHostState
    ) {
        val snackbarData = remember(it) {
            state.currentSnackbarData
        }

        snackbarData?.let {
            ProjectSnackbarContent(
                modifier = Modifier.padding(ProjectSnackbarDefaults.snackbarPadding),
                type = snackbarData.type,
                message = snackbarData.message,
                actionLabel = snackbarData.actionLabel,
                onActionClicked = snackbarData::performAction
            )
        }
    }
}

@Composable
private fun ProjectSnackbarContent(
    message: String,
    type: ProjectSnackbarDefaults.ProjectSnackbarType,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClicked: (() -> Unit)? = null
) {
    Snackbar(
        modifier = modifier
            .border(
                width = ProjectSnackbarDefaults.SNACKBAR_BORDER_WIDTH_DP.dp,
                shape = ProjectSnackbarDefaults.snackbarShape,
                color = type.borderColor
            ),
        shape = ProjectSnackbarDefaults.snackbarShape,
        containerColor = type.containerColor,
        contentColor = type.contentColor,
        action = actionLabel?.let {
            {
                ProjectButton(
                    modifier = Modifier,
                    text = actionLabel,
                    onClick = {
                        onActionClicked?.invoke()
                    },
                    style = ProjectButtonDefaults.ButtonStyle.TEXT,
                    tint = when (type) {
                        ProjectSnackbarDefaults.ProjectSnackbarType.SUCCESS ->
                            ProjectButtonDefaults.ButtonTint.SUCCESS

                        ProjectSnackbarDefaults.ProjectSnackbarType.ERROR ->
                            ProjectButtonDefaults.ButtonTint.ERROR
                    },
                    size = ProjectButtonDefaults.ButtonSize.SMALL
                )
            }
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                painter = type.painter,
                contentDescription = null,
                tint = type.iconColor
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                modifier = Modifier.weight(1f),
                text = message,
                style = ProjectSnackbarDefaults.textStyle
            )
        }
    }
}

private class ProjectSnackbarPreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectSnackbarPreviewData>(
        collection = buildList {
            ProjectSnackbarDefaults.ProjectSnackbarType.entries.forEach { type ->
                add(
                    ProjectSnackbarPreviewData(
                        type = type,
                    )
                )
            }
        }
    )

private data class ProjectSnackbarPreviewData(
    val type: ProjectSnackbarDefaults.ProjectSnackbarType
)

@ProjectComponentPreview
@Composable
private fun ProjectSnackbarPreview(
    @PreviewParameter(ProjectSnackbarPreviewDataProvider::class) data: ProjectSnackbarPreviewData
) {
    ProjectTheme {
        ProjectSnackbarContent(
            message = "message",
            type = data.type,
        )
    }
}

@ProjectComponentPreview
@Composable
private fun ProjectSnackbarWithActionPreview(
    @PreviewParameter(ProjectSnackbarPreviewDataProvider::class) data: ProjectSnackbarPreviewData
) {
    ProjectTheme {
        ProjectSnackbarContent(
            message = "message",
            type = data.type,
            actionLabel = "action",
            onActionClicked = {}
        )
    }
}