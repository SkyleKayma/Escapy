package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInAsGuestUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignInFromEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.SignUpUseCase
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType
import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
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

            try {
                signInFromEmailProviderUseCase(
                    email = email,
                    password = password
                )

                _state.update {
                    it.copy(
                        event = SignInEvent.SignInSuccess
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(
                        event = SignInEvent.SignInError(e.message),
                    )
                }
            } finally {
                _state.update {
                    it.copy(isButtonLoading = false)
                }
            }
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch {
            _state.update {
                it.copy(isButtonLoading = true)
            }

            try {
                signInAsGuestUseCase()

                _state.update {
                    it.copy(event = SignInEvent.SignUpSuccess)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(event = SignInEvent.SignUpError(e.message))
                }
            } finally {
                _state.update {
                    it.copy(isButtonLoading = false)
                }
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

            try {
                signUpUseCase(
                    email = email,
                    password = password
                )

                _state.update {
                    it.copy(
                        event = SignInEvent.SignUpSuccess
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(
                        event = SignInEvent.SignUpError(e.message),
                    )
                }
            } finally {
                _state.update {
                    it.copy(isButtonLoading = false)
                }
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
        _state.update {
            it.copy(event = null)
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