package fr.skyle.escapy.designsystem.core.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme
import fr.skyle.escapy.designsystem.theme.SmallShapeToken

object ProjectSnackbarDefaults {
    private const val SNACKBAR_PADDING_DP = 20

    /**
     * See [androidx.compose.material3.tokens.SnackbarTokens].SingleLineContainerHeight
     * and calculated on [ProjectSnackbarDefaults.textStyle] font size
     */
    private const val SNACKBAR_INTERNAL_VERTICAL_PADDING = 12
    private const val SNACKBAR_INTERNAL_HORIZONTAL_PADDING = 16

    /**
     * See [androidx.compose.material3.Snackbar].TextEndExtraSpacing
     * and calculated on [ProjectSnackbarDefaults.textStyle] font size
     */
    private const val SNACKBAR_WITH_BUTTON_INTERNAL_START_PADDING = 16
    private const val SNACKBAR_WITH_BUTTON_INTERNAL_TOP_PADDING = 12
    private const val SNACKBAR_WITH_BUTTON_INTERNAL_END_PADDING = 8
    private const val SNACKBAR_WITH_BUTTON_INTERNAL_BOTTOM_PADDING = 12

    /**
     * See [androidx.compose.material3.tokens.SnackbarTokens].ContainerElevation
     */
    const val SNACKBAR_ELEVATION_DP = 6

    /**
     * See [androidx.compose.material3.tokens.SnackbarTokens].IconSize
     */
    const val SNACKBAR_ICON_SIZE = 24

    const val SNACKBAR_PADDING_BETWEEN_ICON_AND_TEXT_DP = 12
    const val SNACKBAR_PADDING_BETWEEN_TEXT_AND_BUTTON_DP = 8

    const val SNACKBAR_BORDER_WIDTH_DP = 1

    enum class ProjectSnackbarType {
        NEUTRAL,
        SUCCESS,
        ERROR;

        val containerColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL ->
                    ProjectTheme.colors.surfaceContainerHigh

                SUCCESS ->
                    ProjectTheme.colors.surfaceContainerHigh

                ERROR ->
                    ProjectTheme.colors.surfaceContainerHigh
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL ->
                    ProjectTheme.colors.onSurface

                SUCCESS ->
                    ProjectTheme.colors.onSurface

                ERROR ->
                    ProjectTheme.colors.onSurface
            }

        val borderColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL ->
                    ProjectTheme.colors.black

                SUCCESS ->
                    ProjectTheme.colors.success

                ERROR ->
                    ProjectTheme.colors.error
            }
    }

    val textStyle: TextStyle
        @Composable
        get() = ProjectTheme.typography.p3

    val snackbarShape: Shape =
        SmallShapeToken

    val snackbarPadding = PaddingValues(SNACKBAR_PADDING_DP.dp)

    val snackbarInternalPadding = PaddingValues(
        vertical = SNACKBAR_INTERNAL_VERTICAL_PADDING.dp,
        horizontal = SNACKBAR_INTERNAL_HORIZONTAL_PADDING.dp
    )

    val snackbarWithButtonInternalPadding = PaddingValues(
        start = SNACKBAR_WITH_BUTTON_INTERNAL_START_PADDING.dp,
        top = SNACKBAR_WITH_BUTTON_INTERNAL_TOP_PADDING.dp,
        end = SNACKBAR_WITH_BUTTON_INTERNAL_END_PADDING.dp,
        bottom = SNACKBAR_WITH_BUTTON_INTERNAL_BOTTOM_PADDING.dp
    )
}