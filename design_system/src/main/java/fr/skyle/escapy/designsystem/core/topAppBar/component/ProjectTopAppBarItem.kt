package fr.skyle.escapy.designsystem.core.topAppBar.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButton
import fr.skyle.escapy.designsystem.core.iconButton.ProjectIconButtonDefaults
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectTopAppBarItem(
    icon: Painter,
    onClick: () -> Unit,
    style: ProjectIconButtonDefaults.Style,
    modifier: Modifier = Modifier,
    iconColor: Color? = null,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
) {
    ProjectIconButton(
        modifier = modifier,
        icon = icon,
        onClick = onClick,
        style = style,
        tint = ProjectIconButtonDefaults.Tint.NEUTRAL,
        size = ProjectIconButtonDefaults.Size.LARGE,
        iconColor = iconColor,
        isEnabled = isEnabled,
        isLoading = isLoading,
    )
}

@Preview
@Composable
private fun ProjectTopAppBarItemPreview() {
    ProjectTheme {
        ProjectTopAppBarItem(
            icon = rememberVectorPainter(Icons.AutoMirrored.Filled.ArrowBack),
            onClick = {},
            style = ProjectIconButtonDefaults.Style.OUTLINED
        )
    }
}