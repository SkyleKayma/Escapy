package fr.skyle.escapy.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangeEmailForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangeEmailForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangePasswordForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangePasswordForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromAnonymousProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromAnonymousProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.DeleteAccountFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.LinkAccountWithEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.LinkAccountWithEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInAsGuestUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.SignOutUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignOutUseCaseImpl
import fr.skyle.escapy.data.usecase.firebaseAuth.SignUpUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignUpUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.CreateLobbyUseCase
import fr.skyle.escapy.data.usecase.lobby.CreateLobbyUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.FetchLobbiesForCurrentUserUseCase
import fr.skyle.escapy.data.usecase.lobby.FetchLobbiesForCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.FetchLobbyUseCase
import fr.skyle.escapy.data.usecase.lobby.FetchLobbyUseCaseImpl
import fr.skyle.escapy.data.usecase.lobby.WatchCurrentUserActiveLobbiesUseCase
import fr.skyle.escapy.data.usecase.lobby.WatchCurrentUserActiveLobbiesUseCaseImpl
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCase
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.FetchUserUseCase
import fr.skyle.escapy.data.usecase.user.FetchUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.UpdateRemoteAvatarUseCase
import fr.skyle.escapy.data.usecase.user.UpdateRemoteRemoteAvatarUseCaseImpl
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.WatchUserUseCase
import fr.skyle.escapy.data.usecase.user.WatchUserUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun bindsSignInFromEmailProviderUseCase(
        signInFromEmailProviderUseCaseImpl: SignInFromEmailProviderUseCaseImpl,
    ): SignInFromEmailProviderUseCase

    @Binds
    @Singleton
    fun bindsSignInAsGuestUseCase(
        signInAsGuestUseCaseImpl: SignInAsGuestUseCaseImpl,
    ): SignInAsGuestUseCase

    @Binds
    @Singleton
    fun bindsSignUpUseCase(
        signUpUseCaseImpl: SignUpUseCaseImpl,
    ): SignUpUseCase

    @Binds
    @Singleton
    fun bindsSignOutUseCase(
        signOutUseCaseImpl: SignOutUseCaseImpl,
    ): SignOutUseCase

    @Binds
    @Singleton
    fun bindsChangeEmailForEmailProviderUseCase(
        changeEmailForEmailProviderUseCaseImpl: ChangeEmailForEmailProviderUseCaseImpl,
    ): ChangeEmailForEmailProviderUseCase

    @Binds
    @Singleton
    fun bindsChangePasswordForEmailProviderUseCase(
        changePasswordForEmailProviderUseCaseImpl: ChangePasswordForEmailProviderUseCaseImpl,
    ): ChangePasswordForEmailProviderUseCase

    @Binds
    @Singleton
    fun bindsDeleteAccountFromAnonymousProviderUseCase(
        deleteAccountFromAnonymousProviderUseCaseImpl: DeleteAccountFromAnonymousProviderUseCaseImpl,
    ): DeleteAccountFromAnonymousProviderUseCase

    @Binds
    @Singleton
    fun bindsDeleteAccountFromEmailPasswordProviderUseCase(
        deleteAccountFromEmailPasswordProviderUseCaseImpl: DeleteAccountFromEmailProviderUseCaseImpl,
    ): DeleteAccountFromEmailProviderUseCase

    @Binds
    @Singleton
    fun bindsFetchUserUseCase(
        fetchUserUseCaseImpl: FetchUserUseCaseImpl,
    ): FetchUserUseCase

    @Binds
    @Singleton
    fun bindsFetchCurrentUserUseCase(
        fetchCurrentUserUseCaseImpl: FetchCurrentUserUseCaseImpl,
    ): FetchCurrentUserUseCase

    @Binds
    @Singleton
    fun bindsUpdateRemoteAvatarUseCase(
        updateRemoteAvatarUseCaseImpl: UpdateRemoteRemoteAvatarUseCaseImpl,
    ): UpdateRemoteAvatarUseCase

    @Binds
    @Singleton
    fun bindsWatchUserUseCase(
        watchUserUseCaseImpl: WatchUserUseCaseImpl,
    ): WatchUserUseCase

    @Binds
    @Singleton
    fun bindsWatchCurrentUserUseCase(
        watchCurrentUserUseCaseImpl: WatchCurrentUserUseCaseImpl,
    ): WatchCurrentUserUseCase

    @Binds
    @Singleton
    fun bindsLinkAccountWithEmailProviderUseCase(
        linkAccountWithEmailProviderUseCaseImpl: LinkAccountWithEmailProviderUseCaseImpl,
    ): LinkAccountWithEmailProviderUseCase

    @Binds
    @Singleton
    fun bindsCreateLobbyUseCase(
        createLobbyUseCaseImpl: CreateLobbyUseCaseImpl,
    ): CreateLobbyUseCase

    @Binds
    @Singleton
    fun bindsFetchLobbyUseCase(
        fetchLobbyUseCaseImpl: FetchLobbyUseCaseImpl,
    ): FetchLobbyUseCase

    @Binds
    @Singleton
    fun bindsFetchLobbiesForCurrentUserUseCase(
        fetchLobbiesForCurrentUserUseCaseImpl: FetchLobbiesForCurrentUserUseCaseImpl,
    ): FetchLobbiesForCurrentUserUseCase

    @Binds
    @Singleton
    fun bindsWatchCurrentUserActiveLobbiesUseCase(
        watchCurrentUserActiveLobbiesUseCaseImpl: WatchCurrentUserActiveLobbiesUseCaseImpl,
    ): WatchCurrentUserActiveLobbiesUseCase
}