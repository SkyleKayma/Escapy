package fr.skyle.escapy.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun getTextWithSpannedPart(
    fullText: String,
    spannedTexts: List<Pair<String, SpanStyle>>,
): AnnotatedString =
    buildAnnotatedString {
        append(fullText)
        spannedTexts.forEach { (spannedText, spanStyle) ->
            val split = fullText.split(spannedText)
            var splitIndex = 0
            split.forEachIndexed { index, element ->
                if (index < split.lastIndex) {
                    splitIndex += element.length + spannedText.length

                    addStyle(
                        style = spanStyle,
                        start = splitIndex - spannedText.length,
                        end = splitIndex
                    )
                }
            }
        }
    }

@Composable
fun getTextWithHighlightedColorPart(
    fullText: String,
    highlightedTexts: List<String>,
    highlightedColor: Color
): AnnotatedString =
    getTextWithSpannedPart(
        fullText = fullText,
        spannedTexts = highlightedTexts.map {
            it to SpanStyle(color = highlightedColor)
        }
    )

@Composable
fun getTextWithHighlightedColorPart(
    fullText: String,
    highlightedText: String,
    highlightedColor: Color
): AnnotatedString =
    getTextWithHighlightedColorPart(
        fullText = fullText,
        highlightedTexts = listOf(highlightedText),
        highlightedColor = highlightedColor
    )