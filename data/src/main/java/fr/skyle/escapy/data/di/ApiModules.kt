package fr.skyle.escapy.data.di

import org.koin.dsl.module

object ApiModules {

    private val restModule = module {

    }

    private val restApiModule = module {

    }

    val modules = listOf(
        restModule,
        restApiModule
    )
}