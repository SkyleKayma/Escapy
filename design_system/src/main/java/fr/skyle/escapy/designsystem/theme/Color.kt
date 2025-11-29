package fr.skyle.escapy.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val Primary = Color(0xFFF5B856)
val OnPrimary = Color(0xFF000000)
val Secondary = Color(0xff2b3033)
val OnSecondary = Color(0xFFFFFFFF)

val SurfaceContainerHigh = Color(0xff2b3033)
val SurfaceContainerLow = Color(0xFF23272A)

val OnSurface = Color(0xFFFFFFFF)

val Error = Color(0xffEA3A24)
val Warning = Color(0xFF664D03)
val Success = Color(0xFF0F9C4D)

val White = Color(0xFFFFFFFF)
val Black = Color(0xFF000000)

val Grey750: Color = Color(0xFF333333)
val Grey500: Color = Color(0xFF666666)
val Grey250: Color = Color(0xFFE0E0E0)

@Immutable
data class ProjectColors(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerLow: Color,
    val onSurface: Color,
    val error: Color,
    val warning: Color,
    val success: Color,
    val white: Color,
    val black: Color,
    val grey750: Color,
    val grey500: Color,
    val grey250: Color,
)

internal val LocalColors = staticCompositionLocalOf {
    ProjectColors(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        surfaceContainerHigh = Color.Unspecified,
        surfaceContainerLow = Color.Unspecified,
        onSurface = Color.Unspecified,
        error = Color.Unspecified,
        warning = Color.Unspecified,
        success = Color.Unspecified,
        white = Color.Unspecified,
        black = Color.Unspecified,
        grey750 = Color.Unspecified,
        grey500 = Color.Unspecified,
        grey250 = Color.Unspecified,
    )
}

val DarkColorScheme = ProjectColors(
    primary = Primary,
    onPrimary = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerLow = SurfaceContainerLow,
    onSurface = OnSurface,
    error = Error,
    warning = Warning,
    success = Success,
    white = White,
    black = Black,
    grey750 = Grey750,
    grey500 = Grey500,
    grey250 = Grey250,
)