package fr.skyle.escapy.log

import timber.log.Timber


object TimberLogImplementation : TimberLog {

    fun init() {
        Timber.plant(object : Timber.DebugTree() {
            override fun createStackElementTag(element: StackTraceElement): String =
                String.format("C:%s:%s", super.createStackElementTag(element), element.lineNumber)
        })
    }
}