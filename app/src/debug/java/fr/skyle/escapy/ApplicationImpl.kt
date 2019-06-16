package fr.skyle.escapy

import com.crashlytics.android.Crashlytics
import com.crashlytics.android.core.CrashlyticsCore
import fr.skyle.escapy.injection.DebugModules
import io.fabric.sdk.android.Fabric
import org.koin.dsl.koinApplication
import timber.log.Timber


class ApplicationImpl : CustomApplication() {

    override fun onCreate() {
        super.onCreate()

        koinApplication {
            modules(listOf(DebugModules.firebaseModule, DebugModules.serviceModule))
        }
    }

    override fun plantTimber() {
        Timber.plant(Timber.DebugTree())
    }

    override fun initializeCrashlytics() {
        val core = CrashlyticsCore.Builder().disabled(true).build()
        val crashlytics = Crashlytics.Builder().core(core).build()
        Fabric.with(this, crashlytics)
    }
}