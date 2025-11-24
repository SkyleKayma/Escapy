package fr.skyle.escapy.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.R
import fr.skyle.escapy.data.di.qualifiers.GithubUrl
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl

@Module
@InstallIn(SingletonComponent::class)
internal object APIModule {

    // Toolkit

    @GithubUrl
    @Provides
    fun githubUrl(@ApplicationContext context: Context): HttpUrl =
        context.getString(R.string.github_api_project_repository_url).toHttpUrl()
}