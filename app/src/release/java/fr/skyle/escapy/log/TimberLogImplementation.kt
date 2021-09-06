package fr.skyle.escapy.log

import timber.log.Timber

object TimberLogImplementation : TimberLog {

    fun init() {
        Timber.plant(CrashReportingTree())
    }
}