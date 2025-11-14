package fr.skyle.escapy.designsystem.core.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectButton(
    text: String,
    onClick: () -> Unit,
    style: ProjectButtonDefaults.ProjectButtonStyle,
    tint: ProjectButtonDefaults.ProjectButtonTint,
    size: ProjectButtonDefaults.ProjectButtonSize,
    modifier: Modifier = Modifier,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    val content: @Composable () -> Unit = {
        ProjectButtonContent(
            text = text,
            textStyle = ProjectButtonDefaults.getTextStyle(
                style = style,
                size = size
            ),
            isLoading = isLoading,
            textDecoration = style.textDecoration,
            size = size,
            leadingContent = leadingContent,
            trailingContent = trailingContent,
        )
    }

    when (style) {
        ProjectButtonDefaults.ProjectButtonStyle.FILLED -> {
            ProjectFilledButton(
                modifier = modifier,
                onClick = onClick,
                tint = tint,
                size = size,
                isEnabled = isEnabled,
                content = content
            )
        }

        ProjectButtonDefaults.ProjectButtonStyle.OUTLINED -> {
            ProjectOutlinedButton(
                modifier = modifier,
                onClick = onClick,
                tint = tint,
                size = size,
                isEnabled = isEnabled,
                content = content
            )
        }

        ProjectButtonDefaults.ProjectButtonStyle.TEXT -> {
            ProjectTextButton(
                modifier = modifier,
                onClick = onClick,
                tint = tint,
                size = size,
                isEnabled = isEnabled,
                content = content
            )
        }
    }
}

@Composable
private fun ProjectFilledButton(
    onClick: () -> Unit,
    tint: ProjectButtonDefaults.ProjectButtonTint,
    size: ProjectButtonDefaults.ProjectButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        shape = ProjectButtonDefaults.buttonShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = tint.containerColor,
            contentColor = tint.contentColor,
            disabledContainerColor = ProjectButtonDefaults.disabledContainerColor,
            disabledContentColor = ProjectButtonDefaults.disabledContentColor,
        ),
        contentPadding = size.contentPadding,
    ) {
        content()
    }
}

@Composable
private fun ProjectTextButton(
    onClick: () -> Unit,
    tint: ProjectButtonDefaults.ProjectButtonTint,
    size: ProjectButtonDefaults.ProjectButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    TextButton(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        shape = ProjectButtonDefaults.buttonShape,
        colors = ButtonDefaults.textButtonColors(
            contentColor = tint.containerColor,
            disabledContentColor = ProjectButtonDefaults.disabledContentColor,
        ),
        contentPadding = size.contentPadding,
    ) {
        content()
    }
}

@Composable
private fun ProjectOutlinedButton(
    onClick: () -> Unit,
    tint: ProjectButtonDefaults.ProjectButtonTint,
    size: ProjectButtonDefaults.ProjectButtonSize,
    isEnabled: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        enabled = isEnabled,
        shape = ProjectButtonDefaults.buttonShape,
        border = BorderStroke(
            width = ProjectButtonDefaults.BUTTON_BORDER_WIDTH_DP.dp,
            color = if (isEnabled) {
                tint.borderColor
            } else {
                ProjectButtonDefaults.disabledContentColor
            }
        ),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = tint.outlinedContentColor,
            disabledContentColor = ProjectButtonDefaults.disabledContentColor,
        ),
        contentPadding = size.contentPadding,
    ) {
        content()
    }
}

@Composable
private fun ProjectButtonContent(
    text: String,
    textStyle: TextStyle,
    textDecoration: TextDecoration?,
    size: ProjectButtonDefaults.ProjectButtonSize,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = modifier.size(ProjectIconButtonDefaults.ICON_SIZE_DP.dp),
                color = LocalContentColor.current,
            )
        } else {
            leadingContent?.let {
                Box(
                    modifier = Modifier.size(size.iconSize),
                    contentAlignment = Alignment.Center
                ) {
                    leadingContent()
                }
            }

            Text(
                modifier = Modifier.weight(1f, fill = false),
                text = text,
                style = textStyle,
                textDecoration = textDecoration,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            trailingContent?.let {
                Box(
                    modifier = Modifier.height(size.iconSize),
                    contentAlignment = Alignment.Center
                ) {
                    trailingContent()
                }
            }
        }
    }
}

private class ProjectButtonPreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectButtonPreviewData>(
        collection = buildList {
            ProjectButtonDefaults.ProjectButtonStyle.entries.forEach { style ->
                ProjectButtonDefaults.ProjectButtonTint.entries.forEach { tint ->
                    ProjectButtonDefaults.ProjectButtonSize.entries.forEach { size ->
                        Boolean.values.forEach { isEnabled ->
                            Boolean.values.forEach { isLoading ->
                                add(
                                    ProjectButtonPreviewData(
                                        style = style,
                                        tint = tint,
                                        size = size,
                                        isEnabled = isEnabled,
                                        isLoading = isLoading,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    )

private data class ProjectButtonPreviewData(
    val style: ProjectButtonDefaults.ProjectButtonStyle,
    val tint: ProjectButtonDefaults.ProjectButtonTint,
    val size: ProjectButtonDefaults.ProjectButtonSize,
    val isEnabled: Boolean,
    val isLoading: Boolean,
)

@Preview
@Composable
private fun ProjectButtonPreview(
    @PreviewParameter(ProjectButtonPreviewDataProvider::class) data: ProjectButtonPreviewData
) {
    ProjectTheme {
        ProjectButton(
            text = "Text",
            onClick = { },
            leadingContent = {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Refresh),
                    contentDescription = null
                )
            },
            trailingContent = {
                Icon(
                    painter = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowRight),
                    contentDescription = null
                )
            },
            style = data.style,
            tint = data.tint,
            size = data.size,
            isEnabled = data.isEnabled,
            isLoading = data.isLoading
        )
    }
}