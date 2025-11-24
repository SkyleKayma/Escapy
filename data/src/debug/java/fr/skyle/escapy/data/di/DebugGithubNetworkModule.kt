package fr.skyle.escapy.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.di.qualifiers.GithubOkHttpClient
import fr.skyle.escapy.data.interceptor.HttpLogInterceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DebugGithubNetworkModule {

    @GithubOkHttpClient
    @Provides
    @Singleton
    fun provideGithubHttpClient(
        okHttpBuilder: OkHttpClient.Builder,
        httpLogInterceptor: HttpLogInterceptor
    ): OkHttpClient {
        return okHttpBuilder
            .addInterceptor(httpLogInterceptor)
            .build()
    }
}