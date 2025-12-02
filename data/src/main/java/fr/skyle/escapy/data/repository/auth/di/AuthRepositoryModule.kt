package fr.skyle.escapy.data.repository.auth.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.repository.auth.AuthRemoteDataSourceImpl
import fr.skyle.escapy.data.repository.auth.AuthRepositoryImpl
import fr.skyle.escapy.data.repository.auth.api.AuthRemoteDataSource
import fr.skyle.escapy.data.repository.auth.api.AuthRepository

@Module
@InstallIn(SingletonComponent::class)
interface AuthRepositoryModule {

    @Binds
    fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl,
    ): AuthRepository

    @Binds
    fun bindsAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl,
    ): AuthRemoteDataSource
}