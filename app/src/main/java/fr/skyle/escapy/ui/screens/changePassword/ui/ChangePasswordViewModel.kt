package fr.skyle.escapy.ui.screens.changePassword.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.PASSWORD_VALID_LENGTH
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangePasswordForEmailProviderUseCase
import fr.skyle.escapy.data.usecase.firebaseAuth.ChangePasswordForEmailProviderUseCaseResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordForEmailProviderUseCase: ChangePasswordForEmailProviderUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    fun changePassword() {
        viewModelScope.launch {
            markAllFieldsChecked()

            if (!areFieldsValid()) {
                _state.update {
                    it.copy(event = ChangePasswordEvent.InvalidFields)
                }
                return@launch
            }

            _state.update {
                it.copy(isButtonLoading = true)
            }

            val response = changePasswordForEmailProviderUseCase(
                currentPassword = _state.value.currentPassword,
                newPassword = _state.value.newPassword
            )

            when (response) {
                is ChangePasswordForEmailProviderUseCaseResponse.Error ->
                    _state.update {
                        it.copy(event = ChangePasswordEvent.Error(response.message))
                    }

                ChangePasswordForEmailProviderUseCaseResponse.InvalidCurrentPassword ->
                    _state.update {
                        it.copy(event = ChangePasswordEvent.InvalidCurrentPassword)
                    }

                ChangePasswordForEmailProviderUseCaseResponse.Success ->
                    _state.update {
                        it.copy(event = ChangePasswordEvent.Success)
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
                currentPasswordValidationState = it.currentPasswordValidationState.copy(
                    hasBeenChecked = true
                ),
                newPasswordValidationState = it.newPasswordValidationState.copy(hasBeenChecked = true),
                newPasswordConfirmationValidationState = it.newPasswordConfirmationValidationState.copy(
                    hasBeenChecked = true
                )
            )
        }
    }

    private fun areFieldsValid(): Boolean {
        val state = _state.value
        return state.currentPasswordValidationState.isValid &&
                state.newPasswordValidationState.isValid &&
                state.newPasswordConfirmationValidationState.isValid
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

    fun setNewPassword(newPassword: String) {
        validateNewPassword(newPassword = newPassword)
        validateNewPasswordConfirmation(
            newPassword = newPassword,
            newPasswordConfirmation = _state.value.newPasswordConfirmation
        )

        _state.update {
            it.copy(
                newPassword = newPassword
            )
        }
    }

    private fun validateNewPassword(newPassword: String) {
        _state.update {
            it.copy(
                newPasswordValidationState = it.newPasswordValidationState.copy(
                    hasBeenChecked = false,
                    isLongEnough = newPassword.length >= PASSWORD_VALID_LENGTH,
                    containsUppercase = newPassword.any { it.isUpperCase() },
                    containsSpecialCharacter = newPassword.any { !it.isLetterOrDigit() && !it.isWhitespace() },
                    containsDigit = newPassword.any { it.isDigit() }
                )
            )
        }
    }

    fun setNewPasswordConfirmation(newPasswordConfirmation: String) {
        validateNewPasswordConfirmation(
            newPassword = _state.value.newPassword,
            newPasswordConfirmation = newPasswordConfirmation
        )

        _state.update {
            it.copy(
                newPasswordConfirmation = newPasswordConfirmation
            )
        }
    }

    private fun validateNewPasswordConfirmation(
        newPassword: String,
        newPasswordConfirmation: String,
    ) {
        _state.update {
            it.copy(
                newPasswordConfirmationValidationState = it.newPasswordConfirmationValidationState.copy(
                    hasBeenChecked = false,
                    isSameAsNewPassword = newPassword == newPasswordConfirmation
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
        val currentPassword: String = "",
        val newPassword: String = "",
        val newPasswordConfirmation: String = "",
        val currentPasswordValidationState: CurrentPasswordValidationState = CurrentPasswordValidationState(),
        val newPasswordValidationState: NewPasswordValidationState = NewPasswordValidationState(),
        val newPasswordConfirmationValidationState: NewPasswordConfirmationValidationState = NewPasswordConfirmationValidationState(),
        val event: ChangePasswordEvent? = null,
    )

    data class CurrentPasswordValidationState(
        val hasBeenChecked: Boolean = false,
        val isBlank: Boolean = true,
    ) {
        val isValid: Boolean
            get() = !isBlank
    }

    data class NewPasswordValidationState(
        val hasBeenChecked: Boolean = false,
        val isLongEnough: Boolean = false,
        val containsUppercase: Boolean = false,
        val containsSpecialCharacter: Boolean = false,
        val containsDigit: Boolean = false,
    ) {
        val isValid: Boolean
            get() = isLongEnough && containsUppercase && containsSpecialCharacter && containsDigit
    }

    data class NewPasswordConfirmationValidationState(
        val hasBeenChecked: Boolean = false,
        val isSameAsNewPassword: Boolean = false,
    ) {
        val isValid: Boolean
            get() = isSameAsNewPassword
    }

    sealed interface ChangePasswordEvent {
        data object Success : ChangePasswordEvent
        data object InvalidFields : ChangePasswordEvent
        data object InvalidCurrentPassword : ChangePasswordEvent
        data class Error(val message: String?) : ChangePasswordEvent
    }
}