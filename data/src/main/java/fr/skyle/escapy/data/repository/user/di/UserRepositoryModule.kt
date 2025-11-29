package fr.skyle.escapy.data.repository.user.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.repository.user.UserLocalDataSourceImpl
import fr.skyle.escapy.data.repository.user.UserRemoteDataSourceImpl
import fr.skyle.escapy.data.repository.user.UserRepositoryImpl
import fr.skyle.escapy.data.repository.user.api.UserLocalDataSource
import fr.skyle.escapy.data.repository.user.api.UserRemoteDataSource
import fr.skyle.escapy.data.repository.user.api.UserRepository

@Module
@InstallIn(SingletonComponent::class)
interface UserRepositoryModule {

    @Binds
    fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl,
    ): UserRepository

    @Binds
    fun bindsUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl,
    ): UserLocalDataSource

    @Binds
    fun bindsUserRemoteDataSource(
        userRemoteDataSourceImpl: UserRemoteDataSourceImpl,
    ): UserRemoteDataSource
}