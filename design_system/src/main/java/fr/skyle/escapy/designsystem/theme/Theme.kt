package fr.skyle.escapy.designsystem.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.luminance

@Composable
fun ProjectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // TODO Light theme will come later
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> DarkColorScheme
    }

    val rippleConfiguration =
        RippleConfiguration(
            color = ProjectTheme.colors.black,
            rippleAlpha = if (ProjectTheme.colors.black.luminance() > 0.5) {
                RippleAlpha(
                    pressedAlpha = 0.24f,
                    focusedAlpha = 0.24f,
                    draggedAlpha = 0.16f,
                    hoveredAlpha = 0.08f
                )
            } else {
                RippleAlpha(
                    pressedAlpha = 0.12f,
                    focusedAlpha = 0.12f,
                    draggedAlpha = 0.08f,
                    hoveredAlpha = 0.04f
                )
            }
        )

    CompositionLocalProvider(
        LocalColors provides colorScheme,
        LocalTypography provides CustomTypography,
        LocalIndication provides ripple(),
        LocalRippleConfiguration provides rippleConfiguration,
        content = content
    )
}

object ProjectTheme {
    val colors: ProjectColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: ProjectTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}