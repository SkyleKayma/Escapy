package fr.skyle.escapy.ui.screens.changePassword.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.PASSWORD_VALID_LENGTH
import fr.skyle.escapy.data.repository.auth.api.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _changePasswordState = MutableStateFlow<ChangePasswordState>(ChangePasswordState())
    val changePasswordState: StateFlow<ChangePasswordState> by lazy { _changePasswordState.asStateFlow() }

    fun changePassword() {
        viewModelScope.launch {
            _changePasswordState.update {
                it.copy(
                    currentPasswordValidationState = it.currentPasswordValidationState.copy(
                        hasBeenChecked = true
                    ),
                    newPasswordValidationState = it.newPasswordValidationState.copy(
                        hasBeenChecked = true
                    ),
                    newPasswordConfirmationValidationState = it.newPasswordConfirmationValidationState.copy(
                        hasBeenChecked = true
                    )
                )
            }

            val currentPasswordValidationState =
                _changePasswordState.value.currentPasswordValidationState
            val newPasswordValidationState =
                _changePasswordState.value.newPasswordValidationState
            val newPasswordConfirmationValidationState =
                _changePasswordState.value.newPasswordConfirmationValidationState

            if (!currentPasswordValidationState.isValid
                || !newPasswordValidationState.isValid
                || !newPasswordConfirmationValidationState.isValid
            ) {
                _changePasswordState.update {
                    it.copy(
                        event = ChangePasswordEvent.InvalidFields
                    )
                }

                return@launch
            }

            _changePasswordState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                authRepository.changePasswordForEmailPasswordProvider(
                    currentPassword = _changePasswordState.value.currentPassword,
                    newPassword = _changePasswordState.value.newPassword
                ).getOrThrow()

                _changePasswordState.update {
                    it.copy(
                        event = ChangePasswordEvent.Success,
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _changePasswordState.update {
                    it.copy(
                        event = ChangePasswordEvent.Error(e.message)
                    )
                }
            } finally {
                _changePasswordState.update {
                    it.copy(
                        isButtonLoading = false
                    )
                }
            }
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _changePasswordState.update {
                it.copy(event = null)
            }
        }
    }

    fun setCurrentPassword(currentPassword: String) {
        validateCurrentPassword(currentPassword = currentPassword)

        _changePasswordState.update {
            it.copy(
                currentPassword = currentPassword
            )
        }
    }

    private fun validateCurrentPassword(currentPassword: String) {
        _changePasswordState.update {
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
            newPasswordConfirmation = _changePasswordState.value.newPasswordConfirmation
        )

        _changePasswordState.update {
            it.copy(
                newPassword = newPassword
            )
        }
    }

    private fun validateNewPassword(newPassword: String) {
        _changePasswordState.update {
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
            newPassword = _changePasswordState.value.newPassword,
            newPasswordConfirmation = newPasswordConfirmation
        )

        _changePasswordState.update {
            it.copy(
                newPasswordConfirmation = newPasswordConfirmation
            )
        }
    }

    private fun validateNewPasswordConfirmation(
        newPassword: String,
        newPasswordConfirmation: String,
    ) {
        _changePasswordState.update {
            it.copy(
                newPasswordConfirmationValidationState = it.newPasswordConfirmationValidationState.copy(
                    hasBeenChecked = false,
                    isSameAsNewPassword = newPassword == newPasswordConfirmation
                )
            )
        }
    }

    data class ChangePasswordState(
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
        data class Error(val message: String?) : ChangePasswordEvent
    }
}