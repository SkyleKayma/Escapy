package fr.skyle.escapy.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import fr.skyle.escapy.data.rest.EscapyApi
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object ApiModules {

    private val restModule = module {
        // Urls
        factory {
            "https://www.google.fr".toHttpUrl()
//            BuildConfig.REMOTE_SERVICE_URL.toHttpUrl()
        }

        // Retrofit
        single {
            Json {
                ignoreUnknownKeys = true
            }
        }

        factory {
            OkHttpClient.Builder().cache(get())
                .readTimeout(1, TimeUnit.MINUTES)
                .build()
        }

        factory {
            Retrofit.Builder()
                .baseUrl(get<HttpUrl>().newBuilder().build())
                .callFactory { request ->
                    get<OkHttpClient>().newCall(request)
                }
                .addConverterFactory(get<Json>().asConverterFactory("application/json".toMediaType()))
                .build()
        }
    }

    private val restApiModule = module {
        // Api interfaces
        factory {
            get<Retrofit>().create(EscapyApi::class.java)
        }
    }

    val modules = listOf(
        restModule,
        restApiModule
    )
}