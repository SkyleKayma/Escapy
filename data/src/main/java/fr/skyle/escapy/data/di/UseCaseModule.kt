package fr.skyle.escapy.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.usecase.account.ChangeEmailForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.ChangeEmailForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.account.ChangePasswordForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.ChangePasswordForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.account.DeleteAccountFromAnonymousProviderUseCase
import fr.skyle.escapy.data.usecase.account.DeleteAccountFromAnonymousProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.account.DeleteAccountFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.DeleteAccountFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.account.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.account.SignInAsGuestUseCaseImpl
import fr.skyle.escapy.data.usecase.account.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.SignInFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.account.SignOutUseCase
import fr.skyle.escapy.data.usecase.account.SignOutUseCaseImpl
import fr.skyle.escapy.data.usecase.account.SignUpUseCase
import fr.skyle.escapy.data.usecase.account.SignUpUseCaseImpl
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCase
import fr.skyle.escapy.data.usecase.user.FetchCurrentUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.FetchUserUseCase
import fr.skyle.escapy.data.usecase.user.FetchUserUseCaseImpl
import fr.skyle.escapy.data.usecase.user.UpdateAvatarUseCase
import fr.skyle.escapy.data.usecase.user.UpdateAvatarUseCaseImpl
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
    fun bindsUpdateAvatarUseCase(
        updateAvatarUseCaseImpl: UpdateAvatarUseCaseImpl,
    ): UpdateAvatarUseCase

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
}