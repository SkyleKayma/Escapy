package fr.skyle.escapy.ui.screens.changePassword.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.repository.user.api.UserRepository
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
    private val userRepository: UserRepository
) : ViewModel() {

    private val _changePasswordState = MutableStateFlow<ChangePasswordState>(ChangePasswordState())
    val changePasswordState: StateFlow<ChangePasswordState> = _changePasswordState.asStateFlow()

    fun changePassword(
        currentPassword: String,
        newPassword: String,
    ) {
        viewModelScope.launch {
            _changePasswordState.update {
                it.copy(isButtonLoading = true)
            }

            try {
                userRepository.changePassword(
                    currentPassword = currentPassword,
                    newPassword = newPassword
                ).getOrThrow()

                _changePasswordState.update {
                    it.copy(
                        event = ChangePasswordEvent.Success
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

    data class ChangePasswordState(
        val isButtonLoading: Boolean = false,
        val event: ChangePasswordEvent? = null
    )

    sealed interface ChangePasswordEvent {
        data object Success : ChangePasswordEvent
        data class Error(val errorMessage: String?) : ChangePasswordEvent
    }
}