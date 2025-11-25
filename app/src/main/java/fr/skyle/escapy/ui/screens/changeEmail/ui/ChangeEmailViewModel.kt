package fr.skyle.escapy.ui.screens.changeEmail.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
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
class ChangeEmailViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _changeEmailState = MutableStateFlow<ChangeEmailState>(ChangeEmailState())
    val changeEmailState: StateFlow<ChangeEmailState> by lazy { _changeEmailState.asStateFlow() }

    fun saveProfile() {
        viewModelScope.launch {
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

            val newEmailValidationState =
                _changeEmailState.value.newEmailValidationState
            val currentPasswordValidationState =
                _changeEmailState.value.currentPasswordValidationState

            if (!currentPasswordValidationState.isValid || !newEmailValidationState.isValid) {
                _changeEmailState.update {
                    it.copy(
                        event = ChangeEmailEvent.Error(
                            ChangeEmailError.InvalidFields
                        )
                    )
                }

                return@launch
            }

            _changeEmailState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                authRepository.changeEmailForEmailPasswordProvider(
                    newEmail = _changeEmailState.value.newEmail,
                    currentPassword = _changeEmailState.value.currentPassword,
                ).getOrThrow()

                _changeEmailState.update {
                    it.copy(
                        event = ChangeEmailEvent.Success
                    )
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _changeEmailState.update {
                    it.copy(
                        event = ChangeEmailEvent.Error(
                            ChangeEmailError.Error(e.message)
                        )
                    )
                }
            } finally {
                _changeEmailState.update {
                    it.copy(
                        isButtonLoading = false
                    )
                }
            }
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _changeEmailState.update {
                it.copy(event = null)
            }
        }
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
        data object Success : ChangeEmailEvent
        data class Error(val error: ChangeEmailError) : ChangeEmailEvent
    }

    sealed interface ChangeEmailError {
        data class Error(val errorMessage: String?) : ChangeEmailError
        data object InvalidFields : ChangeEmailError
    }
}