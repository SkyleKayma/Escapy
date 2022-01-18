package fr.skyle.escapy

import android.app.Application
import fr.skyle.escapy.log.TimberLogImplementation


abstract class EscapyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    // ---
    // Initializations

    open fun initTimber() {
        TimberLogImplementation.init()
    }
}