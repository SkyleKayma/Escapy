package fr.skyle.escapy.ui.screens.changeEmail.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.account.ChangeEmailForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.account.ChangeEmailForEmailProviderUseCaseResponse
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

    private val _changeEmailState = MutableStateFlow<ChangeEmailState>(ChangeEmailState())
    val changeEmailState: StateFlow<ChangeEmailState> by lazy { _changeEmailState.asStateFlow() }

    fun saveProfile() {
        viewModelScope.launch {
            markAllFieldsChecked()

            if (!areFieldsValid()) {
                _changeEmailState.update {
                    it.copy(event = ChangeEmailEvent.InvalidFields)
                }

                return@launch
            }

            _changeEmailState.update {
                it.copy(isButtonLoading = true)
            }

            val response = changeEmailForEmailProviderUseCase(
                newEmail = _changeEmailState.value.currentPassword,
                currentPassword = _changeEmailState.value.currentPassword
            )

            when (response) {
                is ChangeEmailForEmailProviderUseCaseResponse.Error -> {
                    _changeEmailState.update {
                        it.copy(event = ChangeEmailEvent.Error(response.message))
                    }
                }

                ChangeEmailForEmailProviderUseCaseResponse.InvalidCurrent -> {
                    _changeEmailState.update {
                        it.copy(event = ChangeEmailEvent.InvalidCurrentPassword)
                    }
                }

                ChangeEmailForEmailProviderUseCaseResponse.EmailVerificationSent -> {
                    _changeEmailState.update {
                        it.copy(event = ChangeEmailEvent.EmailVerificationSent)
                    }
                }
            }
        }
    }

    private fun markAllFieldsChecked() {
        _changeEmailState.update {
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
        val state = _changeEmailState.value
        return state.newEmailValidationState.isValid &&
                state.currentPasswordValidationState.isValid
    }

    fun setNewEmail(newEmail: String) {
        validateNewEmail(newEmail = newEmail)

        _changeEmailState.update {
            it.copy(
                newEmail = newEmail
            )
        }
    }

    private fun validateNewEmail(newEmail: String) {
        _changeEmailState.update {
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

        _changeEmailState.update {
            it.copy(
                currentPassword = currentPassword
            )
        }
    }

    private fun validateCurrentPassword(currentPassword: String) {
        _changeEmailState.update {
            it.copy(
                currentPasswordValidationState = it.currentPasswordValidationState.copy(
                    hasBeenChecked = false,
                    isBlank = currentPassword.isBlank()
                )
            )
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _changeEmailState.update {
                it.copy(event = null)
            }
        }
    }

    data class ChangeEmailState(
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