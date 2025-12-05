package fr.skyle.escapy.designsystem.core.textField

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectClickableTextField(
    value: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingContent: @Composable (ProjectTextFieldSideContentScope.() -> Unit)? = null,
    trailingContent: @Composable (ProjectTextFieldSideContentScope.() -> Unit)? = null,
    label: String? = null,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: ProjectTextFieldDefaults.TextFieldShape = ProjectTextFieldDefaults.TextFieldShape.RECTANGLE,
    colors: ProjectTextFieldDefaults.Colors = ProjectTextFieldDefaults.Colors.default()
) {
    ProjectTextField(
        modifier = modifier,
        value = value,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        label = label,
        onClick = onClick,
        isClickable = isEnabled,
        isEnabled = false,
        isError = isError,
        isReadOnly = true,
        isSingleLine = true,
        visualTransformation = visualTransformation,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        shape = shape,
        colors = ProjectTextFieldDefaults.Colors.default(
            labelColor = colors.labelColor,
            disabledLabelColor = if (isEnabled) {
                ProjectTextFieldDefaults.defaultContentColor
            } else {
                colors.disabledContentColor
            },
            containerColor = colors.containerColor,
            disabledContainerColor = if (isEnabled) {
                ProjectTextFieldDefaults.defaultContainerColor
            } else {
                colors.disabledContainerColor
            },
            contentColor = colors.contentColor,
            disabledContentColor = if (isEnabled) {
                ProjectTextFieldDefaults.defaultContentColor
            } else {
                colors.disabledContentColor
            },
            borderColor = colors.borderColor,
            disabledBorderColor = if (isError) {
                ProjectTextFieldDefaults.defaultErrorBorderColor
            } else if (isEnabled) {
                ProjectTextFieldDefaults.defaultBorderColor
            } else {
                colors.disabledBorderColor
            },
        )
    )
}

@Preview
@Composable
private fun ProjectClickableTextFieldPreview() {
    ProjectTheme {
        ProjectClickableTextField(
            label = "Label",
            value = "",
            onClick = {},
            isError = true
        )
    }
}