package fr.skyle.escapy

import android.app.Application
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp
import fr.skyle.escapy.log.FirebaseCrashReportingTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
open class CustomApplication : Application() {

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    open fun initTimber() {
        Timber.plant(FirebaseCrashReportingTree(crashlytics))
    }
}