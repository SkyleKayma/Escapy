package fr.skyle.escapy

import android.app.Application
import com.crashlytics.android.Crashlytics
import fr.skyle.escapy.di.Modules.firebaseModule
import fr.skyle.escapy.di.Modules.serviceModule
import fr.skyle.escapy.log.CrashReportingTree
import io.fabric.sdk.android.Fabric
import org.koin.dsl.koinApplication
import timber.log.Timber


abstract class CustomApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        koinApplication {
            modules(listOf(firebaseModule, serviceModule))
        }

        initializeCrashlytics()

        setupTimberLogging()
    }

    open fun initializeCrashlytics() {
        Fabric.with(applicationContext, Crashlytics())
    }

    protected open fun setupTimberLogging() {
        Timber.plant(CrashReportingTree())
    }
}
