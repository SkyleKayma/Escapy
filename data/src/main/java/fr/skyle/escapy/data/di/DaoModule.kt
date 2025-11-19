package fr.skyle.escapy.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.db.ProjectDatabase
import fr.skyle.escapy.data.db.dao.UserDao

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    fun provideUserDao(projectDatabase: ProjectDatabase): UserDao =
        projectDatabase.currentUserDao()
}