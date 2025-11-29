package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.account.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.account.SignInAsGuestUseCaseResponse
import fr.skyle.escapy.data.usecase.account.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.SignInFromEmailProviderUseCaseResponse
import fr.skyle.escapy.data.usecase.account.SignUpUseCase
import fr.skyle.escapy.data.usecase.account.SignUpUseCaseResponse
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType
import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val signInAsGuestUseCase: SignInAsGuestUseCase,
    private val signInFromEmailProviderUseCase: SignInFromEmailProviderUseCase,
    private val signUpUseCase: SignUpUseCase,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<Route.SignIn>()

    private val _signInState = MutableStateFlow<SignInState>(
        SignInState(
            signInReasonEvent = route.reason?.let { SignInReasonEvent.FromReason(it) }
        )
    )
    val signInState: StateFlow<SignInState> by lazy { _signInState.asStateFlow() }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            val response = signInFromEmailProviderUseCase(
                email = email,
                password = password
            )

            when (response) {
                is SignInFromEmailProviderUseCaseResponse.Error -> {
                    _signInState.update {
                        it.copy(
                            event = SignInEvent.SignInError(response.message),
                        )
                    }
                }

                SignInFromEmailProviderUseCaseResponse.Success -> {
                    _signInState.update {
                        it.copy(
                            event = SignInEvent.SignInSuccess
                        )
                    }
                }
            }

            _signInState.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            when (val response = signInAsGuestUseCase()) {
                is SignInAsGuestUseCaseResponse.Error -> {
                    _signInState.update {
                        it.copy(event = SignInEvent.SignUpError(response.message))
                    }
                }

                SignInAsGuestUseCaseResponse.Success -> {
                    _signInState.update {
                        it.copy(event = SignInEvent.SignUpSuccess)
                    }
                }
            }

            _signInState.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
            // TODO
        }
    }

    fun signUp(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            val response = signUpUseCase(
                email = email,
                password = password
            )

            when (response) {
                is SignUpUseCaseResponse.Error -> {
                    _signInState.update {
                        it.copy(
                            event = SignInEvent.SignUpError(response.message),
                        )
                    }
                }

                SignUpUseCaseResponse.Success -> {
                    _signInState.update {
                        it.copy(
                            event = SignInEvent.SignUpSuccess
                        )
                    }
                }
            }

            _signInState.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun changeAuthType() {
        val newAuthType = when (_signInState.value.authType) {
            AuthType.SIGN_IN -> AuthType.SIGN_UP
            AuthType.SIGN_UP -> AuthType.SIGN_IN
        }

        _signInState.update {
            it.copy(
                authType = newAuthType
            )
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _signInState.update {
                it.copy(event = null)
            }
        }
    }

    fun signInReasonEventDelivered() {
        viewModelScope.launch {
            _signInState.update {
                it.copy(signInReasonEvent = null)
            }
        }
    }

    data class SignInState(
        val authType: AuthType = AuthType.SIGN_IN,
        val isButtonLoading: Boolean = false,
        val event: SignInEvent? = null,
        val signInReasonEvent: SignInReasonEvent? = null
    )

    sealed interface SignInEvent {
        data class SignInError(val message: String?) : SignInEvent
        data class SignUpError(val message: String?) : SignInEvent
        data object SignInSuccess : SignInEvent
        data object SignUpSuccess : SignInEvent
    }

    sealed interface SignInReasonEvent {
        data class FromReason(val signInReason: SignInReason) : SignInReasonEvent
    }
}