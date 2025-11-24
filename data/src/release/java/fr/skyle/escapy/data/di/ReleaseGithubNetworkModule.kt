package fr.skyle.escapy.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.di.qualifiers.GithubOkHttpClient
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ReleaseGithubNetworkModule {

    @GithubOkHttpClient
    @Provides
    @Singleton
    fun provideGithubHttpClient(
        okHttpBuilder: OkHttpClient.Builder
    ): OkHttpClient =
        okHttpBuilder.build()
}