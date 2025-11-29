package fr.skyle.escapy.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.skyle.escapy.data.usecase.ChangeEmailForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.ChangeEmailForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.ChangePasswordForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.ChangePasswordForEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.DeleteAccountFromAnonymousProviderUseCase
import fr.skyle.escapy.data.usecase.DeleteAccountFromAnonymousProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.DeleteAccountFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.DeleteAccountFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.SignInAsGuestUseCaseImpl
import fr.skyle.escapy.data.usecase.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.SignInFromEmailProviderUseCaseImpl
import fr.skyle.escapy.data.usecase.SignOutUseCase
import fr.skyle.escapy.data.usecase.SignOutUseCaseImpl
import fr.skyle.escapy.data.usecase.SignUpUseCase
import fr.skyle.escapy.data.usecase.SignUpUseCaseImpl
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
}