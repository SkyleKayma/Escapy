package fr.skyle.escapy.util

object HourMinuteUtils {

    fun getTwoDigitsString(value: Int): String {
        return if (value.toString().length < 2) {
            "0$value"
        } else value.toString()
    }
}