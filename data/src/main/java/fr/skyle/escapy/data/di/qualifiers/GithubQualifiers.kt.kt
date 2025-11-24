package fr.skyle.escapy.data.di.qualifiers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubRetrofit