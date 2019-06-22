package fr.skyle.escapy.ext

import android.widget.TextView
import java.util.*

fun TextView.textToLowerCase(locale: Locale = Locale.getDefault()) {
    text = text.toString().toLowerCase(locale)
}

fun TextView.textToUpperCase(locale: Locale = Locale.getDefault()) {
    text = text.toString().toUpperCase(locale)
}

fun TextView.textTrimmed(): String {
    return text.toString().trim()
}