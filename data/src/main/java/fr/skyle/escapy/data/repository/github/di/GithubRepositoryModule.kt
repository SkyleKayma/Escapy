package fr.skyle.escapy.data.repository.github.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.repository.github.GithubRepositoryImpl
import fr.skyle.escapy.data.repository.github.api.GithubRepository

@Module
@InstallIn(SingletonComponent::class)
interface GithubRepositoryModule {

    @Binds
    fun bindsGithubRepository(
        githubRepositoryImpl: GithubRepositoryImpl,
    ): GithubRepository
}