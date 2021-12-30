package fr.skyle.escapy.log

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

/**
 * Created by Openium on 20/03/2018.
 */

class CrashReportingTree(private val firebaseCrashlytics: FirebaseCrashlytics) : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR || priority == Log.WARN) {
            t?.let {
                firebaseCrashlytics.log(message)
                firebaseCrashlytics.recordException(it)
            }
        }
    }
}