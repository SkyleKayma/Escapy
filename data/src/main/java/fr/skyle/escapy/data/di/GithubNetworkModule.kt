package fr.skyle.escapy.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.di.qualifiers.GithubOkHttpClient
import fr.skyle.escapy.data.di.qualifiers.GithubRetrofit
import fr.skyle.escapy.data.di.qualifiers.GithubUrl
import fr.skyle.escapy.data.json
import fr.skyle.escapy.data.rest.github.GithubAPI
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object GithubNetworkModule {

    @GithubRetrofit
    @Provides
    @Singleton
    fun provideToolkitRetrofit(
        @GithubUrl url: HttpUrl,
        @GithubOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(
        @GithubRetrofit retrofit: Retrofit
    ): GithubAPI =
        retrofit.create(GithubAPI::class.java)
}