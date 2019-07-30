package fr.skyle.escapy.ext

import fr.skyle.escapy.util.HourMinuteUtils

data class Duration(var hour: Int, var minute: Int) {

    val time: Long
        get() = hour * 60L * 60L * 1000L + minute * 60L * 1000L

    val hourTwoDigits: String
        get() = HourMinuteUtils.getTwoDigitsString(hour)

    val minuteTwoDigits: String
        get() = HourMinuteUtils.getTwoDigitsString(minute)
}
