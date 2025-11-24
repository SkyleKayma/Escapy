package fr.skyle.escapy.ui.screens.signIn.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.repository.user.api.UserRepository
import fr.skyle.escapy.ui.screens.signIn.ui.enums.AuthType
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInState>(SignInState())
    val signInState: StateFlow<SignInState> = _signInState.asStateFlow()

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                // TODO: Replace with actual sign in logic
//                userRepository.insertUser("fake_token", email)

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
                // TODO: Replace with actual sign up logic
//                userRepository.insertUser("fake_token", email)

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
                        event = SignInEvent.SignUpError(e.message ?: "An error occurred"),
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
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            try {
//                userRepository.insertUser("fake_google_token", "Google User name")
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
                        event = SignInEvent.SignInError(e.message ?: "An error occurred"),
                    )
                }
            } finally {
                _signInState.update {
                    it.copy(isButtonLoading = false)
                }
            }
        }
    }

    fun signInAsGuest() {
        viewModelScope.launch {
            _signInState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                userRepository.signInAsGuest().getOrThrow()

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

    data class SignInState(
        val authType: AuthType = AuthType.SIGN_IN,
        val isButtonLoading: Boolean = false,
        val event: SignInEvent? = null
    )

    sealed interface SignInEvent {
        data class SignInError(val errorMessage: String?) : SignInEvent
        data class SignUpError(val errorMessage: String?) : SignInEvent
        data object SignInSuccess : SignInEvent
        data object SignUpSuccess : SignInEvent
    }
}