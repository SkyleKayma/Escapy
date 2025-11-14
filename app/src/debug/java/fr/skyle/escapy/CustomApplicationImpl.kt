package fr.skyle.escapy

import timber.log.Timber

class CustomApplicationImpl : CustomApplication() {

    override fun initTimber() {
        super.initTimber()
        Timber.plant(Timber.DebugTree())
    }
}