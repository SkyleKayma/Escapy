package fr.skyle.escapy.designsystem.core.iconButton

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectIconButtonDefaults {
    internal const val BUTTON_LARGE_SIZE_DP = 38
    internal const val BUTTON_SMALL_SIZE_DP = 30
    internal const val ICON_LARGE_SIZE_DP = 20
    internal const val ICON_SMALL_SIZE_DP = 20
    const val ICON_BUTTON_BORDER_WIDTH_DP = 1

    enum class Style {
        OUTLINED,
        FILLED,
        SIMPLE
    }

    enum class Tint {
        NEUTRAL,
        DARK;

        val containerColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.grey500
                DARK -> ProjectTheme.colors.surfaceContainerHigh
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.black
                DARK -> ProjectTheme.colors.onSurface
            }

        val borderColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL -> ProjectTheme.colors.grey500
                DARK -> ProjectTheme.colors.grey500
            }
    }

    enum class Size {
        SMALL,
        LARGE;

        val buttonSize: Dp
            get() = when (this) {
                SMALL -> BUTTON_SMALL_SIZE_DP.dp
                LARGE -> BUTTON_LARGE_SIZE_DP.dp
            }

        val iconSize: Dp
            get() = when (this) {
                SMALL -> {
                    ICON_SMALL_SIZE_DP.dp
                }

                LARGE -> {
                    ICON_LARGE_SIZE_DP.dp
                }
            }
    }

    val iconButtonShape: Shape = CircleShape

    val disabledContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.grey750

    val disabledContentColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerLow
}