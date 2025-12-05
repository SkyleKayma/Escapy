package fr.skyle.escapy.designsystem.core.timePicker

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectTimePickerDialogDefaults {

    val dialogShape: Shape
        @Composable
        get() = ProjectTheme.shape.large

    @ConsistentCopyVisibility
    data class Colors private constructor(
        val containerColor: Color,
        val contentColor: Color,
        val selectedContainerColor: Color,
        val selectedContentColor: Color,
        val unselectedContainerColor: Color,
        val borderColor: Color,
    ) {
        companion object {
            @Composable
            fun default(
                containerColor: Color = ProjectTheme.colors.surfaceContainerLow,
                contentColor: Color = ProjectTheme.colors.onSurface,
                selectedContainerColor: Color = ProjectTheme.colors.primary,
                selectedContentColor: Color = ProjectTheme.colors.onPrimary,
                unselectedContainerColor: Color = ProjectTheme.colors.surfaceContainerHigh,
                borderColor: Color = ProjectTheme.colors.surfaceContainerHigh,
            ): Colors {
                return Colors(
                    containerColor = containerColor,
                    contentColor = contentColor,
                    selectedContainerColor = selectedContainerColor,
                    selectedContentColor = selectedContentColor,
                    unselectedContainerColor = unselectedContainerColor,
                    borderColor = borderColor
                )
            }

            @OptIn(ExperimentalMaterial3Api::class)
            @Composable
            fun Colors.toTimePickerColors(): TimePickerColors {
                return TimePickerDefaults.colors(
                    containerColor = containerColor,
                    periodSelectorBorderColor = borderColor,
                    periodSelectorSelectedContainerColor = selectedContainerColor,
                    periodSelectorUnselectedContainerColor = unselectedContainerColor,
                    periodSelectorSelectedContentColor = selectedContentColor,
                    periodSelectorUnselectedContentColor = contentColor,
                    timeSelectorSelectedContainerColor = selectedContainerColor,
                    timeSelectorUnselectedContainerColor = unselectedContainerColor,
                    timeSelectorSelectedContentColor = selectedContentColor,
                    timeSelectorUnselectedContentColor = contentColor,
                    selectorColor = selectedContainerColor,
                    clockDialColor = unselectedContainerColor,
                    clockDialSelectedContentColor = selectedContentColor,
                    clockDialUnselectedContentColor = contentColor,
                )
            }
        }
    }
}