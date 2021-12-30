package fr.skyle.escapy.data.di

import fr.skyle.escapy.data.db.EscapyDatabase
import fr.skyle.escapy.data.repository.NewsRepository
import org.koin.dsl.module

object DataModules {

    private val dbModule = module {
        factory {
            EscapyDatabase.databaseBuilder(get()).build()
        }
    }

    private val repositoryModule = module {
        factory { NewsRepository(get()) }
    }

    private val daoModule = module {
        factory {
            get<EscapyDatabase>().newsDao()
        }
    }

    val modules = listOf(
        dbModule,
        repositoryModule,
        daoModule
    )
}