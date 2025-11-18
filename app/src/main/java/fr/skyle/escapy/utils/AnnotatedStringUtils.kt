package fr.skyle.escapy.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import fr.skyle.escapy.designsystem.theme.ProjectTheme

data class AnnotatedData(
    val text: String,
    val spanStyle: SpanStyle,
    val onClick: (() -> Unit)? = null,
    val ignoreCase: Boolean = false
)

fun buildAnnotatedString(
    fullText: String,
    vararg annotatedData: AnnotatedData,
): AnnotatedString =
    buildAnnotatedString {
        append(fullText)
        annotatedData.forEach { data ->
            val split = fullText.split(data.text, ignoreCase = data.ignoreCase)
            var splitIndex = 0

            split.forEachIndexed { index, element ->
                if (index < split.lastIndex) {
                    splitIndex += element.length + data.text.length

                    addStyle(
                        style = data.spanStyle,
                        start = splitIndex - data.text.length,
                        end = splitIndex
                    )

                    data.onClick?.let { onClick ->
                        addLink(
                            clickable = LinkAnnotation.Clickable(
                                tag = data.text,
                                styles = TextLinkStyles(
                                    style = null,
                                    hoveredStyle = null,
                                    focusedStyle = null,
                                    pressedStyle = null
                                ),
                                linkInteractionListener = {
                                    onClick()
                                }
                            ),
                            start = splitIndex - data.text.length,
                            end = splitIndex
                        )
                    }
                }
            }
        }
    }

@Preview
@Composable
private fun BuildAnnotatedStringPreview() {
    ProjectTheme {
        Text(
            text = buildAnnotatedString(
                fullText = "This is a test, hello world !",
                AnnotatedData(
                    text = "test",
                    spanStyle = SpanStyle(
                        fontWeight = FontWeight.Black,
                        color = Color.Red,
                    ),
                    onClick = {}
                ),
                AnnotatedData(
                    text = "HELLO WORLD",
                    spanStyle = SpanStyle(
                        fontWeight = FontWeight.Thin,
                        color = Color.Blue,
                    ),
                    ignoreCase = true
                )
            )
        )
    }
}