package fr.skyle.escapy

import android.app.Application
import fr.skyle.escapy.data.di.ApiModules
import fr.skyle.escapy.data.di.DataModules
import fr.skyle.escapy.di.AppModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

abstract class EscapyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
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
}