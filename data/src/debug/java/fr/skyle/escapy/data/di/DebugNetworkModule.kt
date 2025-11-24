package fr.skyle.escapy.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.interceptor.HttpLogInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DebugNetworkModule {

    @Provides
    @Singleton
    fun provideHttpLogInterceptor(): HttpLogInterceptor =
        HttpLogInterceptor().apply {
            level = HttpLogInterceptor.Level(
                isRequestLogEnabled = true,
                isCallHeadersLogEnabled = false,
                isCallBodyLogEnabled = true,
                isResponseHeadersLogEnabled = false,
                isResponseBodyLogEnabled = false,
                isCurLEnabled = false
            )
        }
}