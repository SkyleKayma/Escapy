package fr.skyle.escapy.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp
import fr.skyle.escapy.designsystem.R

val Exo2 = FontFamily(
    Font(R.font.exo2_light, FontWeight.Light),
    Font(R.font.exo2_regular, FontWeight.Light),
    Font(R.font.exo2_medium, FontWeight.Light),
    Font(R.font.exo2_semibold, FontWeight.SemiBold),
    Font(R.font.exo2_bold, FontWeight.Bold),
)

@Immutable
data class ProjectTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val b1: TextStyle,
    val b2: TextStyle,
    val p1: TextStyle,
    val p2: TextStyle,
    val p3: TextStyle,
    val buttonS: TextStyle,
    val buttonL: TextStyle,
    val badge: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    ProjectTypography(
        h1 = TextStyle.Default,
        h2 = TextStyle.Default,
        b1 = TextStyle.Default,
        b2 = TextStyle.Default,
        p1 = TextStyle.Default,
        p2 = TextStyle.Default,
        p3 = TextStyle.Default,
        buttonS = TextStyle.Default,
        buttonL = TextStyle.Default,
        badge = TextStyle.Default,
    )
}

val CustomTypography = ProjectTypography(
    h1 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    h2 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 32.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    b1 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    b2 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    p1 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    p2 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    p3 = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    buttonS = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    buttonL = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
    badge = TextStyle(
        fontFamily = Exo2,
        fontWeight = FontWeight.Bold,
        fontSize = 11.sp,
        lineHeight = 8.sp,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None // Ensures the line height is applied even for single-line text
        ),
    ),
)