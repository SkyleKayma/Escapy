package fr.skyle.escapy.di

import fr.skyle.escapy.ui.news.NewsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModules {

    private val viewModelModule = module {
        viewModel { NewsScreenViewModel(get()) }
    }

    val modules = listOf(
        viewModelModule
    )
}