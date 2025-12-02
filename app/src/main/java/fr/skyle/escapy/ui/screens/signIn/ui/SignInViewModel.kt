package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInAsGuestUseCaseResponse
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInFromEmailProviderUseCaseResponse
import fr.skyle.escapy.data.usecase.firebaseAuth.SignUpUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignUpUseCaseResponse
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

    private val _state = MutableStateFlow<State>(
        State(
            signInReasonEvent = route.reason?.let { SignInReasonEvent.FromReason(it) }
        )
    )
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _state.update {
                it.copy(isButtonLoading = true)
            }

            val response = signInFromEmailProviderUseCase(
                email = email,
                password = password
            )

            when (response) {
                is SignInFromEmailProviderUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(
                            event = SignInEvent.SignInError(response.message),
                        )
                    }
                }

                SignInFromEmailProviderUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(
                            event = SignInEvent.SignInSuccess
                        )
                    }
                }
            }

            _state.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch {
            _state.update {
                it.copy(isButtonLoading = true)
            }

            when (val response = signInAsGuestUseCase()) {
                is SignInAsGuestUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = SignInEvent.SignUpError(response.message))
                    }
                }

                SignInAsGuestUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(event = SignInEvent.SignUpSuccess)
                    }
                }
            }

            _state.update {
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
            _state.update {
                it.copy(isButtonLoading = true)
            }

            val response = signUpUseCase(
                email = email,
                password = password
            )

            when (response) {
                is SignUpUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(
                            event = SignInEvent.SignUpError(response.message),
                        )
                    }
                }

                SignUpUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(
                            event = SignInEvent.SignUpSuccess
                        )
                    }
                }
            }

            _state.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    fun changeAuthType() {
        val newAuthType = when (_state.value.authType) {
            AuthType.SIGN_IN -> AuthType.SIGN_UP
            AuthType.SIGN_UP -> AuthType.SIGN_IN
        }

        _state.update {
            it.copy(
                authType = newAuthType
            )
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _state.update {
                it.copy(event = null)
            }
        }
    }

    fun signInReasonEventDelivered() {
        viewModelScope.launch {
            _state.update {
                it.copy(signInReasonEvent = null)
            }
        }
    }

    data class State(
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