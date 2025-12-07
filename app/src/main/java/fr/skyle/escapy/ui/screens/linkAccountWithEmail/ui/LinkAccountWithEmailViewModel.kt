package fr.skyle.escapy.ui.screens.linkAccountWithEmail.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.PASSWORD_VALID_LENGTH
import fr.skyle.escapy.data.usecase.firebaseAuth.LinkAccountWithEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.LinkAccountWithEmailProviderUseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkAccountWithEmailViewModel @Inject constructor(
    private val linkAccountWithEmailProviderUseCase: LinkAccountWithEmailProviderUseCase
) : ViewModel() {

    private val _state =
        MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    fun linkAccountWithEmailProvider() {
        viewModelScope.launch {
            markAllFieldsChecked()

            if (!areFieldsValid()) {
                _state.update {
                    it.copy(event = LinkAccountWithEmailEvent.InvalidFields)
                }
                return@launch
            }

            _state.update {
                it.copy(isButtonLoading = true)
            }

            val response = linkAccountWithEmailProviderUseCase(
                email = _state.value.email,
                password = _state.value.password
            )

            when (response) {
                LinkAccountWithEmailProviderUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(event = LinkAccountWithEmailEvent.Success)
                    }
                }

                LinkAccountWithEmailProviderUseCaseResponse.EmailAlreadyUsed -> {
                    _state.update {
                        it.copy(event = LinkAccountWithEmailEvent.EmailAlreadyUsed)
                    }
                }

                is LinkAccountWithEmailProviderUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = LinkAccountWithEmailEvent.Error(response.message))
                    }
                }
            }

            _state.update {
                it.copy(isButtonLoading = false)
            }
        }
    }

    private fun markAllFieldsChecked() {
        _state.update {
            it.copy(
                emailValidationState = it.emailValidationState.copy(hasBeenChecked = true),
                passwordValidationState = it.passwordValidationState.copy(
                    hasBeenChecked = true
                ),
                passwordConfirmationValidationState = it.passwordConfirmationValidationState.copy(
                    hasBeenChecked = true
                ),
            )
        }
    }

    private fun areFieldsValid(): Boolean {
        val state = _state.value
        return state.emailValidationState.isValid &&
                state.passwordValidationState.isValid &&
                state.passwordConfirmationValidationState.isValid
    }

    fun setEmail(email: String) {
        validateEmail(email = email)

        _state.update {
            it.copy(
                email = email
            )
        }
    }

    private fun validateEmail(email: String) {
        _state.update {
            it.copy(
                emailValidationState = it.emailValidationState.copy(
                    hasBeenChecked = false,
                    isBlank = email.isBlank(),
                    isValidEmailAddress = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                )
            )
        }
    }

    fun setPassword(password: String) {
        validatePassword(password = password)
        validatePasswordConfirmation(
            password = password,
            passwordConfirmation = _state.value.passwordConfirmation
        )

        _state.update {
            it.copy(
                password = password
            )
        }
    }

    private fun validatePassword(password: String) {
        _state.update {
            it.copy(
                passwordValidationState = it.passwordValidationState.copy(
                    hasBeenChecked = false,
                    isLongEnough = password.length >= PASSWORD_VALID_LENGTH,
                    containsUppercase = password.any { it.isUpperCase() },
                    containsSpecialCharacter = password.any { !it.isLetterOrDigit() && !it.isWhitespace() },
                    containsDigit = password.any { it.isDigit() }
                )
            )
        }
    }

    fun setPasswordConfirmation(passwordConfirmation: String) {
        validatePasswordConfirmation(
            password = _state.value.password,
            passwordConfirmation = passwordConfirmation
        )

        _state.update {
            it.copy(
                passwordConfirmation = passwordConfirmation
            )
        }
    }

    private fun validatePasswordConfirmation(
        password: String,
        passwordConfirmation: String,
    ) {
        _state.update {
            it.copy(
                passwordConfirmationValidationState = it.passwordConfirmationValidationState.copy(
                    hasBeenChecked = false,
                    isSameAsNewPassword = password == passwordConfirmation
                )
            )
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(event = null)
        }
    }

    data class State(
        val email: String = "",
        val password: String = "",
        val passwordConfirmation: String = "",
        val emailValidationState: EmailValidationState = EmailValidationState(),
        val passwordValidationState: PasswordValidationState = PasswordValidationState(),
        val passwordConfirmationValidationState: PasswordConfirmationValidationState = PasswordConfirmationValidationState(),
        val isButtonLoading: Boolean = false,
        val event: LinkAccountWithEmailEvent? = null,
    )

    data class EmailValidationState(
        val hasBeenChecked: Boolean = false,
        val isBlank: Boolean = true,
        val isValidEmailAddress: Boolean = true,
    ) {
        val isValid: Boolean
            get() = !isBlank && isValidEmailAddress
    }

    data class PasswordValidationState(
        val hasBeenChecked: Boolean = false,
        val isLongEnough: Boolean = false,
        val containsUppercase: Boolean = false,
        val containsSpecialCharacter: Boolean = false,
        val containsDigit: Boolean = false,
    ) {
        val isValid: Boolean
            get() = isLongEnough && containsUppercase && containsSpecialCharacter && containsDigit
    }

    data class PasswordConfirmationValidationState(
        val hasBeenChecked: Boolean = false,
        val isSameAsNewPassword: Boolean = false,
    ) {
        val isValid: Boolean
            get() = isSameAsNewPassword
    }

    sealed interface LinkAccountWithEmailEvent {
        data object Success : LinkAccountWithEmailEvent
        data object InvalidFields : LinkAccountWithEmailEvent
        data object EmailAlreadyUsed : LinkAccountWithEmailEvent
        data class Error(val message: String?) : LinkAccountWithEmailEvent
    }
}