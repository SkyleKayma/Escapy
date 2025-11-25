package fr.skyle.escapy.ui.core.snackbar

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectSnackbarDefaults {
    private const val SNACKBAR_RADIUS_DP = 8
    private const val SNACKBAR_PADDING_DP = 20
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
                    ProjectTheme.colors.successContainer

                ERROR ->
                    ProjectTheme.colors.errorContainer
            }

        val contentColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL ->
                    ProjectTheme.colors.onSurface

                SUCCESS ->
                    ProjectTheme.colors.success

                ERROR ->
                    ProjectTheme.colors.error
            }

        val borderColor: Color
            @Composable
            get() = when (this) {
                NEUTRAL ->
                    ProjectTheme.colors.black

                SUCCESS ->
                    ProjectTheme.colors.surfaceContainerHigh

                ERROR ->
                    ProjectTheme.colors.surfaceContainerHigh
            }
    }

    val textStyle: TextStyle
        @Composable
        get() = ProjectTheme.typography.p3

    val snackbarShape: Shape = RoundedCornerShape(
        size = SNACKBAR_RADIUS_DP.dp
    )

    val snackbarPadding: PaddingValues = PaddingValues(all = SNACKBAR_PADDING_DP.dp)
}