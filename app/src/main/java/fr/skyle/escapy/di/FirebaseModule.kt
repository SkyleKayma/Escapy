package fr.skyle.escapy.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.BuildConfig
import fr.skyle.escapy.data.di.qualifiers.FirebaseDatabaseUrl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    @FirebaseDatabaseUrl
    fun providesFirebaseDatabaseUrl(): String =
        BuildConfig.firebaseDatabaseUrl

    @Provides
    @Singleton
    fun providesFirebaseCrashlytics(): FirebaseCrashlytics =
        FirebaseCrashlytics.getInstance()
}