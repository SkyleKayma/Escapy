package fr.skyle.escapy.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import fr.skyle.escapy.utils.DateFormat.Companion.getFormatter
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class DateFormat(val pattern: String) {
    LONG_DATE("dd MMMM yyyy");

    companion object {
        fun DateFormat.getFormatter(locale: Locale): DateTimeFormatter {
            return DateTimeFormatter.ofPattern(pattern, locale)
        }
    }
}

fun Long.format(
    format: DateFormat,
    locale: Locale
): String? =
    try {
        format.getFormatter(locale = locale).format(
            Instant
                .ofEpochMilli(this)
                .atZone(ZoneId.of("UTC"))
        )
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

@Composable
fun Long.format(format: DateFormat): String? =
    format(
        format = format,
        locale = LocalConfiguration.current.locales.get(0)
    )

fun ZonedDateTime.format(
    format: DateFormat,
    locale: Locale
): String? =
    try {
        format.getFormatter(locale = locale).format(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

@Composable
fun ZonedDateTime.format(
    format: DateFormat,
): String? =
    format(
        format = format,
        locale = LocalConfiguration.current.locales.get(0)
    )

fun LocalDateTime.format(
    format: DateFormat,
    locale: Locale
): String? =
    try {
        format.getFormatter(locale = locale).format(this)
    } catch (e: Exception) {
        Timber.e(e)
        null
    }

@Composable
fun LocalDateTime.format(
    format: DateFormat,
): String? =
    format(
        format = format,
        locale = LocalConfiguration.current.locales.get(0)
    )