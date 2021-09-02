package fr.skyle.escapy

import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module


class AppImpl : EscapyApplication() {

    override fun initKoin() {
        super.initKoin()
        loadKoinModules(
            mutableListOf<Module>().apply {

            }
        )
    }
}