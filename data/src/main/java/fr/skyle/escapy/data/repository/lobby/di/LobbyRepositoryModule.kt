package fr.skyle.escapy.data.repository.lobby.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.repository.lobby.LobbyLocalDataSourceImpl
import fr.skyle.escapy.data.repository.lobby.LobbyRemoteDataSourceImpl
import fr.skyle.escapy.data.repository.lobby.LobbyRepositoryImpl
import fr.skyle.escapy.data.repository.lobby.api.LobbyLocalDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRemoteDataSource
import fr.skyle.escapy.data.repository.lobby.api.LobbyRepository

@Module
@InstallIn(SingletonComponent::class)
interface LobbyRepositoryModule {

    @Binds
    fun bindsLobbyRepository(
        lobbyRepositoryImpl: LobbyRepositoryImpl,
    ): LobbyRepository

    @Binds
    fun bindsLobbyLocalDataSource(
        lobbyLocalDataSourceImpl: LobbyLocalDataSourceImpl,
    ): LobbyLocalDataSource

    @Binds
    fun bindsLobbyRemoteDataSource(
        lobbyRemoteDataSourceImpl: LobbyRemoteDataSourceImpl,
    ): LobbyRemoteDataSource
}