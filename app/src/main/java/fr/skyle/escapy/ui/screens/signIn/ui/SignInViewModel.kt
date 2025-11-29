package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import fr.skyle.escapy.ui.main.navigation.Route
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType
import fr.skyle.escapy.ui.screens.signIn.ui.enums.SignInReason
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    savedStateHandle: SavedStateHandle
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

            try {
                authRepository.signIn(
                    email = email,
                    password = password
                ).getOrThrow()

                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignInSuccess
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignInError(e.message),
                    )
                }
            } finally {
                _signInState.update {
                    it.copy(isButtonLoading = false)
                }
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                authRepository.signUp(
                    email = email,
                    password = password
                ).getOrThrow()

                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignUpSuccess
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignUpError(e.message),
                    )
                }
            } finally {
                _signInState.update {
                    it.copy(isButtonLoading = false)
                }
            }
        }
    }

    fun signInWithGoogle() {
        viewModelScope.launch {
//            _signInState.update {
//                it.copy(isButtonLoading = true)
//            }
//
//            try {
//                userRepository.insertUser("fake_google_token", "Google User name")
//                _signInState.update {
//                    it.copy(
//                        event = SignInEvent.SignInSuccess
//                    )
//                }
//            } catch (e: CancellationException) {
//                throw e
//            } catch (e: Exception) {
//                Timber.e(e)
//                _signInState.update {
//                    it.copy(
//                        event = SignInEvent.SignInError(e.message),
//                    )
//                }
//            } finally {
//                _signInState.update {
//                    it.copy(isButtonLoading = false)
//                }
//            }
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                authRepository.signUpAsGuest().getOrThrow()

                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignInSuccess
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _signInState.update {
                    it.copy(
                        event = SignInEvent.SignInError(e.message),
                    )
                }
            } finally {
                _signInState.update {
                    it.copy(isButtonLoading = false)
                }
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