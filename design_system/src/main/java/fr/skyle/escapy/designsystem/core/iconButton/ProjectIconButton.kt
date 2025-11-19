package fr.skyle.escapy.designsystem.core.iconButton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectIconButton(
    icon: Painter,
    onClick: () -> Unit,
    style: ProjectIconButtonDefaults.IconButtonStyle,
    tint: ProjectIconButtonDefaults.IconButtonTint,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    iconColor: Color? = null,
) {
    val content: @Composable () -> Unit = {
        ProjectIconButtonContent(
            icon = icon,
            isLoading = isLoading,
        )
    }

    when (style) {
        ProjectIconButtonDefaults.IconButtonStyle.FILLED -> {
            ProjectFilledIconButton(
                modifier = modifier.size(ProjectIconButtonDefaults.BUTTON_SIZE_DP.dp),
                onClick = onClick,
                tint = tint,
                isEnabled = isEnabled,
                content = content,
                iconColor = iconColor
            )
        }

        ProjectIconButtonDefaults.IconButtonStyle.OUTLINED -> {
            ProjectOutlinedIconButton(
                modifier = modifier.size(ProjectIconButtonDefaults.BUTTON_SIZE_DP.dp),
                onClick = onClick,
                tint = tint,
                isEnabled = isEnabled,
                content = content,
                iconColor = iconColor
            )
        }

        ProjectIconButtonDefaults.IconButtonStyle.SIMPLE -> {
            ProjectSimpleIconButton(
                modifier = modifier.size(ProjectIconButtonDefaults.BUTTON_SIZE_DP.dp),
                onClick = onClick,
                tint = tint,
                isEnabled = isEnabled,
                content = content,
                iconColor = iconColor
            )
        }
    }
}

@Composable
private fun ProjectFilledIconButton(
    onClick: () -> Unit,
    tint: ProjectIconButtonDefaults.IconButtonTint,
    isEnabled: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
) {
    FilledIconButton(
        modifier = modifier,
        enabled = isEnabled,
        shape = ProjectIconButtonDefaults.iconButtonShape,
        colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = tint.containerColor,
            contentColor = iconColor ?: tint.contentColor,
            disabledContainerColor = ProjectIconButtonDefaults.disabledContainerColor,
            disabledContentColor = ProjectIconButtonDefaults.disabledContentColor,
        ),
        onClick = onClick,
        content = content
    )
}

@Composable
private fun ProjectOutlinedIconButton(
    onClick: () -> Unit,
    tint: ProjectIconButtonDefaults.IconButtonTint,
    isEnabled: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
) {
    OutlinedIconButton(
        modifier = modifier,
        enabled = isEnabled,
        shape = ProjectIconButtonDefaults.iconButtonShape,
        border = BorderStroke(
            width = ProjectIconButtonDefaults.ICON_BUTTON_BORDER_WIDTH_DP.dp,
            color = if (isEnabled) {
                tint.borderColor
            } else {
                ProjectIconButtonDefaults.disabledContainerColor
            }
        ),
        colors = IconButtonDefaults.outlinedIconButtonColors(
            containerColor = tint.containerColor,
            contentColor = iconColor ?: tint.contentColor,
            disabledContainerColor = ProjectIconButtonDefaults.disabledContainerColor,
            disabledContentColor = ProjectIconButtonDefaults.disabledContentColor,
        ),
        onClick = onClick,
        content = content
    )
}

@Composable
private fun ProjectSimpleIconButton(
    onClick: () -> Unit,
    tint: ProjectIconButtonDefaults.IconButtonTint,
    isEnabled: Boolean,
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
) {
    IconButton(
        modifier = modifier.clip(shape = ProjectIconButtonDefaults.iconButtonShape),
        enabled = isEnabled,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = iconColor ?: tint.contentColor,
            disabledContentColor = ProjectIconButtonDefaults.disabledContentColor,
        ),
        onClick = onClick,
        content = content
    )
}

@Composable
private fun ProjectIconButtonContent(
    icon: Painter,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        CircularProgressIndicator(
            modifier = modifier.size(ProjectIconButtonDefaults.ICON_SIZE_DP.dp),
            color = LocalContentColor.current,
        )
    } else {
        Icon(
            modifier = modifier.size(ProjectIconButtonDefaults.ICON_SIZE_DP.dp),
            painter = icon,
            contentDescription = null
        )
    }
}

private class ProjectIconButtonPreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectIconButtonPreviewData>(
        collection = buildList {
            ProjectIconButtonDefaults.IconButtonStyle.entries.forEach { style ->
                ProjectIconButtonDefaults.IconButtonTint.entries.forEach { tint ->
                    Boolean.values.forEach { isEnabled ->
                        Boolean.values.forEach { isLoading ->
                            add(
                                ProjectIconButtonPreviewData(
                                    style = style,
                                    tint = tint,
                                    isEnabled = isEnabled,
                                    isLoading = isLoading,
                                )
                            )
                        }
                    }
                }
            }
        }
    )

private data class ProjectIconButtonPreviewData(
    val style: ProjectIconButtonDefaults.IconButtonStyle,
    val tint: ProjectIconButtonDefaults.IconButtonTint,
    val isEnabled: Boolean,
    val isLoading: Boolean,
)

@Preview
@Composable
private fun ProjectIconButtonPreview(
    @PreviewParameter(ProjectIconButtonPreviewDataProvider::class) data: ProjectIconButtonPreviewData
) {
    ProjectTheme {
        ProjectIconButton(
            icon = rememberVectorPainter(Icons.Default.Refresh),
            style = data.style,
            tint = data.tint,
            isEnabled = data.isEnabled,
            isLoading = data.isLoading,
            onClick = {},
        )
    }
}