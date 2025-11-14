package fr.skyle.escapy.designsystem.core.iconButton

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectIconButtonDefaults {
    internal const val BUTTON_SIZE_DP = 38
    internal const val ICON_SIZE_DP = 20
    const val ICON_BUTTON_BORDER_WIDTH_DP = 1

    enum class ProjectIconButtonStyle {
        OUTLINED,
        FILLED,
        SIMPLE
    }

    enum class ProjectIconButtonTint {
        NEUTRAL,
        DARK;

        val containerColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.grey500
                DARK -> ProjectTheme.colors.black
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.black
                DARK -> ProjectTheme.colors.white
            }

        val accentColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.white
                DARK -> ProjectTheme.colors.black
            }
    }

    val iconButtonShape: Shape = CircleShape

    val disabledContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.grey250

    val disabledContentColor: Color
        @Composable
        get() = ProjectTheme.colors.grey500
}