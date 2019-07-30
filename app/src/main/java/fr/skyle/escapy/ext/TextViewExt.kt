package fr.skyle.escapy.ext

import android.widget.TextView


fun TextView.textTrimmed(): String {
    return text.toString().trim()
}