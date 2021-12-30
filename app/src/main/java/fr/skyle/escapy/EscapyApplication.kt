package fr.skyle.escapy

import android.app.Application
import fr.skyle.escapy.data.di.ApiModules
import fr.skyle.escapy.data.di.DataModules
import fr.skyle.escapy.di.AppModules
import fr.skyle.escapy.log.TimberLogImplementation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module


abstract class EscapyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
        initTimber()
    }

    // ---
    // Initializations

    open fun initKoin() {
        startKoin {
            androidContext(this@EscapyApplication)

            modules(
                mutableListOf<Module>().apply {
                    addAll(AppModules.modules)
                    addAll(ApiModules.modules)
                    addAll(DataModules.modules)
                }
            )
        }
    }

    open fun initTimber() {
        TimberLogImplementation.init()
    }
}