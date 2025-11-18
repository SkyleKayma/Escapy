package fr.skyle.escapy.designsystem.core.textField

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import fr.skyle.escapy.designsystem.core.textField.ProjectTextFieldDefaults.Colors.Companion.toTextFieldColors
import fr.skyle.escapy.designsystem.ext.values
import fr.skyle.escapy.designsystem.theme.ProjectTheme

@Composable
fun ProjectTextField(
    value: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    leadingContent: @Composable (ProjectTextFieldSideContentScope.() -> Unit)? = null,
    trailingContent: @Composable (ProjectTextFieldSideContentScope.() -> Unit)? = null,
    label: String? = null,
    placeholder: String? = null,
    isEnabled: Boolean = true,
    isError: Boolean = false,
    isReadOnly: Boolean = false,
    isSingleLine: Boolean = true,
    maxLines: Int = if (isSingleLine) {
        ProjectTextFieldDefaults.DEFAULT_SINGLE_LINE_AMOUNT
    } else {
        ProjectTextFieldDefaults.DEFAULT_MULTI_LINE_AMOUNT
    },
    minLines: Int = if (isSingleLine) {
        ProjectTextFieldDefaults.DEFAULT_SINGLE_LINE_AMOUNT
    } else {
        ProjectTextFieldDefaults.DEFAULT_MULTI_LINE_AMOUNT
    },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    shape: ProjectTextFieldDefaults.TextFieldShape = ProjectTextFieldDefaults.TextFieldShape.RECTANGLE,
    colors: ProjectTextFieldDefaults.Colors = ProjectTextFieldDefaults.Colors.default()
) {
    val textStyle = ProjectTheme.typography.p1
    val interactionSource = remember { MutableInteractionSource() }
    val textFieldColors = colors.toTextFieldColors()

    val isFocused = interactionSource.collectIsFocusedAsState().value

    val textColor = textStyle.color.takeOrElse {
        textFieldColors.textColor(
            enabled = isEnabled,
            isError = isError,
            focused = isFocused
        )
    }

    BasicTextField(
        modifier = modifier.defaultMinSize(
            minWidth = ProjectTextFieldDefaults.textFieldMinWidth,
            minHeight = ProjectTextFieldDefaults.textFieldMinHeight
        ),
        value = value,
        onValueChange = onValueChange,
        enabled = isEnabled,
        readOnly = isReadOnly,
        textStyle = textStyle.merge(
            other = TextStyle(color = textColor)
        ),
        cursorBrush = SolidColor(
            value = textFieldColors.cursorColor(isError = isError)
        ),
        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = isSingleLine,
        maxLines = maxLines,
        minLines = minLines,
        decorationBox =
            @Composable { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    visualTransformation = visualTransformation,
                    innerTextField = innerTextField,
                    leadingIcon = leadingContent?.let {
                        {
                            ProjectTextFieldSideContentScopeImpl(
                                textFieldColors = colors,
                                position = ProjectTextFieldContentPosition.LEADING,
                                isEnabled = isEnabled
                            ).leadingContent()
                        }
                    },
                    trailingIcon = trailingContent?.let {
                        {
                            ProjectTextFieldSideContentScopeImpl(
                                textFieldColors = colors,
                                position = ProjectTextFieldContentPosition.TRAILING,
                                isEnabled = isEnabled
                            ).trailingContent()
                        }
                    },
                    label = label?.let {
                        {
                            ProjectTextFieldLabel(
                                modifier = Modifier.wrapContentWidth(),
                                text = label,
                                isFocused = isFocused
                            )
                        }
                    },
                    placeholder = placeholder?.let {
                        {
                            ProjectTextFieldPlaceholder(
                                modifier = Modifier.wrapContentWidth(),
                                text = placeholder,
                            )
                        }
                    },
                    singleLine = isSingleLine,
                    enabled = isEnabled,
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = textFieldColors,
                    container = @Composable {
                        OutlinedTextFieldDefaults.Container(
                            enabled = isEnabled,
                            isError = isError,
                            interactionSource = interactionSource,
                            colors = textFieldColors,
                            shape = shape.shape,
                        )
                    },
                    contentPadding = shape.innerPadding
                )
            }
    )
}

@Composable
fun ProjectTextFieldLabel(
    text: String,
    isFocused: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = if (isFocused) {
            ProjectTheme.typography.b2
        } else {
            ProjectTheme.typography.p1
        }
    )
}

@Composable
fun ProjectTextFieldPlaceholder(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = text,
        style = ProjectTheme.typography.p1
    )
}

@Stable
fun TextFieldColors.cursorColor(isError: Boolean): Color =
    if (isError) errorCursorColor else cursorColor

@Stable
fun TextFieldColors.textColor(
    enabled: Boolean,
    isError: Boolean,
    focused: Boolean,
): Color =
    when {
        !enabled -> disabledTextColor
        isError -> errorTextColor
        focused -> focusedTextColor
        else -> unfocusedTextColor
    }

private class ProjectTextFieldPreviewDataProvider :
    CollectionPreviewParameterProvider<ProjectTextFieldPreviewData>(
        collection = buildList {
            ProjectTextFieldDefaults.TextFieldShape.entries.forEach { shape ->
                Boolean.values.forEach { isEnabled ->
                    Boolean.values.forEach { isReadOnly ->
                        Boolean.values.forEach { isSingleLine ->
                            Boolean.values.forEach { isError ->
                                add(
                                    ProjectTextFieldPreviewData(
                                        shape = shape,
                                        isEnabled = isEnabled,
                                        isReadOnly = isReadOnly,
                                        isSingleLine = isSingleLine,
                                        isError = isError,
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    )

private data class ProjectTextFieldPreviewData(
    val shape: ProjectTextFieldDefaults.TextFieldShape,
    val isError: Boolean,
    val isEnabled: Boolean,
    val isReadOnly: Boolean,
    val isSingleLine: Boolean,
)


@Preview
@Composable
private fun ProjectTextFieldEmptyPreview(
    @PreviewParameter(ProjectTextFieldPreviewDataProvider::class) data: ProjectTextFieldPreviewData
) {
    ProjectTheme {
        ProjectTextField(
            value = "",
            placeholder = "XXX-XX-X",
            isEnabled = data.isEnabled,
            isError = data.isError,
            isReadOnly = data.isReadOnly,
            isSingleLine = data.isSingleLine,
            shape = data.shape,
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun ProjectTextFieldWithLabelPreview(
    @PreviewParameter(ProjectTextFieldPreviewDataProvider::class) data: ProjectTextFieldPreviewData
) {
    ProjectTheme {
        ProjectTextField(
            value = "",
            label = "Label",
            placeholder = "XXX-XX-X",
            isEnabled = data.isEnabled,
            isError = data.isError,
            isReadOnly = data.isReadOnly,
            isSingleLine = data.isSingleLine,
            shape = data.shape,
            onValueChange = {}
        )
    }
}

@Preview
@Composable
private fun ProjectTextFieldPreview(
    @PreviewParameter(ProjectTextFieldPreviewDataProvider::class) data: ProjectTextFieldPreviewData
) {
    ProjectTheme {
        ProjectTextField(
            value = "Value",
            label = "Label",
            leadingContent = {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Person),
                    contentDescription = null
                )
            },
            trailingContent = {
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Person),
                    contentDescription = null
                )
            },
            isEnabled = data.isEnabled,
            isReadOnly = data.isReadOnly,
            isSingleLine = data.isSingleLine,
            isError = data.isError,
            shape = data.shape,
            onValueChange = {}
        )
    }
}