package fr.skyle.escapy

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.skydoves.compose.stability.runtime.ComposeStabilityAnalyzer
import dagger.hilt.android.HiltAndroidApp
import fr.skyle.escapy.data.service.AuthLifecycleObserver
import fr.skyle.escapy.log.FirebaseCrashReportingTree
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
open class CustomApplication : Application() {

    @Inject
    lateinit var crashlytics: FirebaseCrashlytics

    @Inject
    lateinit var authLifecycleObserver: AuthLifecycleObserver

    override fun onCreate() {
        super.onCreate()

        // Enable recomposition tracking ONLY in debug builds
        ComposeStabilityAnalyzer.setEnabled(BuildConfig.DEBUG)

        initTimber()
        initAuthLifecycleObserver()
    }

    open fun initTimber() {
        Timber.plant(FirebaseCrashReportingTree(crashlytics))
    }

    private fun initAuthLifecycleObserver() {
        ProcessLifecycleOwner.get().lifecycle.addObserver(authLifecycleObserver)
    }
}