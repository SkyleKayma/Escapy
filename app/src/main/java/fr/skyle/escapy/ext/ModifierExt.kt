package fr.skyle.escapy.ext

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.R
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun Modifier.boxCardStyle(
    elevation: Dp = 2.dp,
    shape: Shape = ProjectTheme.shape.medium,
    backgroundColor: Color = ProjectTheme.colors.surfaceContainerHigh
): Modifier {
    return this
        .shadow(elevation = elevation, shape = shape)
        .clip(shape)
        .background(backgroundColor)
}

@Composable
fun formatDuration(duration: Long): String {
    val hours = duration / 3600000
    val minutes = (duration % 3600000) / 60000
    return stringResource(R.string.generic_format_duration, hours, minutes)
}