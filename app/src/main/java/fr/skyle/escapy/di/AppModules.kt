package fr.skyle.escapy.di

import fr.skyle.escapy.ui.home.HomeScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    private val viewModelModule = module {
        viewModel { HomeScreenViewModel(get()) }
    }

    val modules = listOf(
        viewModelModule
    )
}