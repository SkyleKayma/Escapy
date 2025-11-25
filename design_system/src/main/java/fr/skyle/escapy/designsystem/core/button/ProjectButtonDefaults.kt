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

    enum class ButtonStyle {
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

    enum class ButtonTint {
        PRIMARY,
        SECONDARY,
        ERROR,
        SUCCESS;

        val containerColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.secondary
                ERROR -> ProjectTheme.colors.errorContainer
                SUCCESS -> ProjectTheme.colors.successContainer
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.onPrimary
                SECONDARY -> ProjectTheme.colors.onSecondary
                ERROR -> ProjectTheme.colors.error
                SUCCESS -> ProjectTheme.colors.success
            }

        val outlineContentColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.onSecondary
                ERROR -> ProjectTheme.colors.error
                SUCCESS -> ProjectTheme.colors.success
            }

        val textContentColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.onSecondary
                ERROR -> ProjectTheme.colors.error
                SUCCESS -> ProjectTheme.colors.success
            }

        val outlineColor: Color
            @Composable
            get() = when (this) {
                PRIMARY -> ProjectTheme.colors.primary
                SECONDARY -> ProjectTheme.colors.secondary
                ERROR -> ProjectTheme.colors.error
                SUCCESS -> ProjectTheme.colors.success
            }
    }

    enum class ButtonSize {
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
    fun getTextStyle(style: ButtonStyle, size: ButtonSize): TextStyle {
        return when (style) {
            ButtonStyle.OUTLINED,
            ButtonStyle.FILLED -> {
                when (size) {
                    ButtonSize.SMALL -> ProjectTheme.typography.buttonS
                    ButtonSize.MEDIUM,
                    ButtonSize.LARGE -> ProjectTheme.typography.buttonL
                }
            }

            ButtonStyle.TEXT -> {
                when (size) {
                    ButtonSize.SMALL -> ProjectTheme.typography.buttonS
                    ButtonSize.MEDIUM,
                    ButtonSize.LARGE -> ProjectTheme.typography.buttonL
                }
            }
        }
    }

    val buttonShape: Shape = RoundedCornerShape(12.dp)

    val disabledContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.grey500

    val disabledContentColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerLow
}