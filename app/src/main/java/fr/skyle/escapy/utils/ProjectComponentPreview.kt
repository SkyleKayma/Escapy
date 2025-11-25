package fr.skyle.escapy.utils

import androidx.compose.ui.tooling.preview.Preview

private const val FRENCH_LOCALE = "fr"

@Retention(AnnotationRetention.BINARY)
@Preview(
    showSystemUi = true,
    locale = FRENCH_LOCALE,
)
annotation class ProjectScreenPreview

@Retention(AnnotationRetention.BINARY)
@Preview(
    locale = FRENCH_LOCALE,
)
annotation class ProjectComponentPreview