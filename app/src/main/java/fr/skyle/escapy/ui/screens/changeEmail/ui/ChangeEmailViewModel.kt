package fr.skyle.escapy.ui.screens.changeEmail.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangeEmailForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangeEmailForEmailProviderUseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeEmailViewModel @Inject constructor(
    private val changeEmailForEmailProviderUseCase: ChangeEmailForEmailProviderUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    fun saveProfile() {
        viewModelScope.launch {
            markAllFieldsChecked()

            if (!areFieldsValid()) {
                _state.update {
                    it.copy(event = ChangeEmailEvent.InvalidFields)
                }

                return@launch
            }

            _state.update {
                it.copy(isButtonLoading = true)
            }

            val response = changeEmailForEmailProviderUseCase(
                newEmail = _state.value.newEmail,
                currentPassword = _state.value.currentPassword
            )

            when (response) {
                is ChangeEmailForEmailProviderUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = ChangeEmailEvent.Error(response.message))
                    }
                }

                ChangeEmailForEmailProviderUseCaseResponse.InvalidCurrent -> {
                    _state.update {
                        it.copy(event = ChangeEmailEvent.InvalidCurrentPassword)
                    }
                }

                ChangeEmailForEmailProviderUseCaseResponse.EmailVerificationSent -> {
                    _state.update {
                        it.copy(event = ChangeEmailEvent.EmailVerificationSent)
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
                newEmailValidationState = it.newEmailValidationState.copy(
                    hasBeenChecked = true
                ),
                currentPasswordValidationState = it.currentPasswordValidationState.copy(
                    hasBeenChecked = true
                ),
            )
        }
    }

    private fun areFieldsValid(): Boolean {
        val state = _state.value
        return state.newEmailValidationState.isValid &&
                state.currentPasswordValidationState.isValid
    }

    fun setNewEmail(newEmail: String) {
        validateNewEmail(newEmail = newEmail)

        _state.update {
            it.copy(
                newEmail = newEmail
            )
        }
    }

    private fun validateNewEmail(newEmail: String) {
        _state.update {
            it.copy(
                newEmailValidationState = it.newEmailValidationState.copy(
                    hasBeenChecked = false,
                    isBlank = newEmail.isBlank(),
                    isValidEmailAddress = Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()
                )
            )
        }
    }

    fun setCurrentPassword(currentPassword: String) {
        validateCurrentPassword(currentPassword = currentPassword)

        _state.update {
            it.copy(
                currentPassword = currentPassword
            )
        }
    }

    private fun validateCurrentPassword(currentPassword: String) {
        _state.update {
            it.copy(
                currentPasswordValidationState = it.currentPasswordValidationState.copy(
                    hasBeenChecked = false,
                    isBlank = currentPassword.isBlank()
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
        val isButtonLoading: Boolean = false,
        val newEmail: String = "",
        val currentPassword: String = "",
        val newEmailValidationState: NewEmailValidationState = NewEmailValidationState(),
        val currentPasswordValidationState: CurrentPasswordValidationState = CurrentPasswordValidationState(),
        val event: ChangeEmailEvent? = null,
    )

    data class CurrentPasswordValidationState(
        val hasBeenChecked: Boolean = false,
        val isBlank: Boolean = true,
    ) {
        val isValid: Boolean
            get() = !isBlank
    }

    data class NewEmailValidationState(
        val hasBeenChecked: Boolean = false,
        val isBlank: Boolean = true,
        val isValidEmailAddress: Boolean = true,
    ) {
        val isValid: Boolean
            get() = !isBlank && isValidEmailAddress
    }

    sealed interface ChangeEmailEvent {
        data object EmailVerificationSent : ChangeEmailEvent
        data object InvalidFields : ChangeEmailEvent
        data object InvalidCurrentPassword : ChangeEmailEvent
        data class Error(val message: String?) : ChangeEmailEvent
    }
}