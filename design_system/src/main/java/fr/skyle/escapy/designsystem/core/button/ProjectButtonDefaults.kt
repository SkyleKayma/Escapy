package fr.skyle.escapy.designsystem.core.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectButtonDefaults {

    private const val BUTTON_HORIZONTAL_PADDING_DP = 16
    private const val BUTTON_SMALL_HORIZONTAL_PADDING_DP = 8
    private const val BUTTON_LARGE_VERTICAL_PADDING_DP = 12
    private const val BUTTON_MEDIUM_VERTICAL_PADDING_DP = 10
    private const val BUTTON_SMALL_VERTICAL_PADDING_DP = 8
    const val BUTTON_LARGE_ICON_SIZE_DP = 20
    const val BUTTON_SMALL_ICON_SIZE_DP = 16
    const val BUTTON_BORDER_WIDTH_DP = 1

    enum class ProjectButtonStyle {
        OUTLINED,
        FILLED,
        TEXT;

        val textDecoration: TextDecoration?
            get() = when (this) {
                OUTLINED -> null
                FILLED -> null
                TEXT -> TextDecoration.Underline
            }
    }

    enum class ProjectButtonTint {
        PRIMARY,
        SECONDARY;

        val containerColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.secondary
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.onPrimary
                SECONDARY -> ProjectTheme.colors.onSecondary
            }

        val outlinedContentColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.secondary
            }

        val borderColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.secondary
            }
    }

    enum class ProjectButtonSize {
        SMALL,
        MEDIUM,
        LARGE;

        val contentPadding: PaddingValues
            get() = when (this) {
                SMALL -> {
                    PaddingValues(
                        horizontal = BUTTON_SMALL_HORIZONTAL_PADDING_DP.dp,
                        vertical = BUTTON_SMALL_VERTICAL_PADDING_DP.dp,
                    )
                }

                MEDIUM -> {
                    PaddingValues(
                        horizontal = BUTTON_HORIZONTAL_PADDING_DP.dp,
                        vertical = BUTTON_MEDIUM_VERTICAL_PADDING_DP.dp,
                    )
                }

                LARGE -> {
                    PaddingValues(
                        horizontal = BUTTON_HORIZONTAL_PADDING_DP.dp,
                        vertical = BUTTON_LARGE_VERTICAL_PADDING_DP.dp,
                    )
                }
            }

        val iconSize: Dp
            get() = when (this) {
                SMALL -> BUTTON_SMALL_ICON_SIZE_DP.dp
                LARGE, MEDIUM -> BUTTON_LARGE_ICON_SIZE_DP.dp
            }
    }

    @Composable
    fun getTextStyle(style: ProjectButtonStyle, size: ProjectButtonSize): TextStyle {
        return when (style) {
            ProjectButtonStyle.OUTLINED,
            ProjectButtonStyle.FILLED -> {
                when (size) {
                    ProjectButtonSize.SMALL -> ProjectTheme.typography.buttonS
                    ProjectButtonSize.MEDIUM,
                    ProjectButtonSize.LARGE -> ProjectTheme.typography.buttonL
                }
            }

            ProjectButtonStyle.TEXT -> {
                when (size) {
                    ProjectButtonSize.SMALL -> ProjectTheme.typography.buttonS
                    ProjectButtonSize.MEDIUM,
                    ProjectButtonSize.LARGE -> ProjectTheme.typography.buttonL
                }
            }
        }
    }

    val buttonShape: Shape = RoundedCornerShape(12.dp)

    val disabledContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.grey250

    val disabledContentColor: Color
        @Composable
        get() = ProjectTheme.colors.white
}