package fr.skyle.escapy.designsystem.core.textField

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import fr.skyle.escapy.designsystem.theme.ProjectTheme

object ProjectTextFieldDefaults {
    const val TEXT_FIELD_RECTANGLE_HORIZONTAL_INNER_PADDING_DP = 12
    const val TEXT_FIELD_RECTANGLE_VERTICAL_INNER_PADDING_DP = 8
    const val DEFAULT_SINGLE_LINE_AMOUNT = 1
    const val DEFAULT_MULTI_LINE_AMOUNT = 5

    val textFieldMinHeight: Dp = OutlinedTextFieldDefaults.MinHeight
    val textFieldMinWidth: Dp = OutlinedTextFieldDefaults.MinWidth

    val defaultContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerHigh

    val defaultDisabledContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerHigh

    val defaultErrorContainerColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerHigh

    val defaultContentColor: Color
        @Composable
        get() = ProjectTheme.colors.onSurface

    val defaultDisabledContentColor: Color
        @Composable
        get() = ProjectTheme.colors.grey500

    val defaultErrorContentColor: Color
        @Composable
        get() = ProjectTheme.colors.error

    val defaultBorderColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerHigh

    val defaultDisabledBorderColor: Color
        @Composable
        get() = ProjectTheme.colors.surfaceContainerHigh

    val defaultErrorBorderColor: Color
        @Composable
        get() = ProjectTheme.colors.error

    val defaultPlaceholderColor: Color
        @Composable
        get() = ProjectTheme.colors.grey250

    val defaultDisabledPlaceholderColor: Color
        @Composable
        get() = ProjectTheme.colors.grey500

    val defaultErrorPlaceholderColor: Color
        @Composable
        get() = ProjectTheme.colors.error

    enum class TextFieldShape {
        RECTANGLE;

        val shape: Shape
            get() {
                return when (this) {
                    RECTANGLE -> RoundedCornerShape(12.dp)
                }
            }

        val innerPadding: PaddingValues
            get() {
                return when (this) {
                    RECTANGLE -> PaddingValues(
                        horizontal = TEXT_FIELD_RECTANGLE_HORIZONTAL_INNER_PADDING_DP.dp,
                        vertical = TEXT_FIELD_RECTANGLE_VERTICAL_INNER_PADDING_DP.dp
                    )
                }
            }
    }

    @ConsistentCopyVisibility
    data class Colors private constructor(
        val containerColor: Color,
        val disabledContainerColor: Color,
        val errorContainerColor: Color,
        val contentColor: Color,
        val disabledContentColor: Color,
        val errorContentColor: Color,
        val borderColor: Color,
        val disabledBorderColor: Color,
        val errorBorderColor: Color,
        val placeholderColor: Color,
        val disabledPlaceholderColor: Color,
        val errorPlaceholderColor: Color,
    ) {
        companion object {
            @Composable
            fun default(
                containerColor: Color = defaultContainerColor,
                disabledContainerColor: Color = defaultDisabledContainerColor,
                errorContainerColor: Color = defaultErrorContainerColor,
                contentColor: Color = defaultContentColor,
                disabledContentColor: Color = defaultDisabledContentColor,
                errorContentColor: Color = defaultErrorContentColor,
                borderColor: Color = defaultBorderColor,
                disabledBorderColor: Color = defaultDisabledBorderColor,
                errorBorderColor: Color = defaultErrorBorderColor,
                placeholderColor: Color = defaultPlaceholderColor,
                disabledPlaceholderColor: Color = defaultDisabledPlaceholderColor,
                errorPlaceholderColor: Color = defaultErrorPlaceholderColor,
            ): Colors {
                return Colors(
                    containerColor = containerColor,
                    disabledContainerColor = disabledContainerColor,
                    errorContainerColor = errorContainerColor,
                    contentColor = contentColor,
                    disabledContentColor = disabledContentColor,
                    errorContentColor = errorContentColor,
                    borderColor = borderColor,
                    disabledBorderColor = disabledBorderColor,
                    errorBorderColor = errorBorderColor,
                    placeholderColor = placeholderColor,
                    disabledPlaceholderColor = disabledPlaceholderColor,
                    errorPlaceholderColor = errorPlaceholderColor,
                )
            }

            @Composable
            fun Colors.toTextFieldColors(): TextFieldColors {
                return OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = borderColor,
                    unfocusedBorderColor = borderColor,
                    disabledBorderColor = disabledBorderColor,
                    errorBorderColor = errorBorderColor,
                    focusedContainerColor = containerColor,
                    unfocusedContainerColor = containerColor,
                    errorContainerColor = errorContainerColor,
                    disabledContainerColor = disabledContainerColor,
                    focusedLeadingIconColor = contentColor,
                    unfocusedLeadingIconColor = contentColor,
                    errorLeadingIconColor = errorContentColor,
                    disabledLeadingIconColor = disabledContentColor,
                    focusedTrailingIconColor = contentColor,
                    unfocusedTrailingIconColor = contentColor,
                    errorTrailingIconColor = errorContentColor,
                    disabledTrailingIconColor = disabledContentColor,
                    focusedTextColor = contentColor,
                    unfocusedTextColor = contentColor,
                    errorTextColor = errorContentColor,
                    disabledTextColor = disabledContentColor,
                    focusedSupportingTextColor = contentColor,
                    unfocusedSupportingTextColor = contentColor,
                    errorSupportingTextColor = errorContentColor,
                    disabledSupportingTextColor = disabledContentColor,
                    cursorColor = contentColor,
                    errorCursorColor = errorContentColor,
                    focusedPlaceholderColor = placeholderColor,
                    unfocusedPlaceholderColor = placeholderColor,
                    disabledPlaceholderColor = disabledPlaceholderColor,
                    errorPlaceholderColor = errorPlaceholderColor,
                )
            }
        }
    }
}