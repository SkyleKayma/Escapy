package fr.skyle.escapy.ui.screens.createLobby.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.usecase.lobby.CreateLobbyUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CreateLobbyViewModel @Inject constructor(
    private val createLobbyUseCase: CreateLobbyUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    fun createLobby() {
        viewModelScope.launch {
            markAllFieldsChecked()

            if (!areFieldsValid()) {
                _state.update {
                    it.copy(event = CreateLobbyEvent.InvalidFields)
                }

                return@launch
            }

            _state.update {
                it.copy(isButtonLoading = true)
            }

            try {
                createLobbyUseCase(
                    title = _state.value.title,
                    duration = _state.value.duration
                )

                _state.update {
                    it.copy(event = CreateLobbyEvent.Success)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _state.update {
                    it.copy(event = CreateLobbyEvent.Error(e.message))
                }
            } finally {
                _state.update {
                    it.copy(isButtonLoading = false)
                }
            }
        }
    }

    private fun markAllFieldsChecked() {
        _state.update {
            it.copy(
                titleValidationState = it.titleValidationState.copy(
                    hasBeenChecked = true
                ),
                durationValidationState = it.durationValidationState.copy(
                    hasBeenChecked = true
                ),
            )
        }
    }

    private fun areFieldsValid(): Boolean {
        val state = _state.value
        return state.titleValidationState.isValid &&
                state.durationValidationState.isValid
    }

    fun setTitle(title: String) {
        validateTitle(title = title)

        _state.update {
            it.copy(
                title = title
            )
        }
    }

    private fun validateTitle(title: String) {
        _state.update {
            it.copy(
                titleValidationState = it.titleValidationState.copy(
                    hasBeenChecked = false,
                    isBlank = title.isBlank()
                )
            )
        }
    }

    fun setDuration(duration: Long) {
        validateDuration(duration = duration)

        _state.update {
            it.copy(
                duration = duration
            )
        }
    }

    private fun validateDuration(duration: Long) {
        _state.update {
            it.copy(
                durationValidationState = it.durationValidationState.copy(
                    hasBeenChecked = false,
                    isValidDuration = duration > 0L,
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
        val title: String = "",
        val duration: Long = 0L,
        val titleValidationState: TitleValidationState = TitleValidationState(),
        val durationValidationState: DurationValidationState = DurationValidationState(),
        val isButtonLoading: Boolean = false,
        val event: CreateLobbyEvent? = null,
    )

    data class TitleValidationState(
        val hasBeenChecked: Boolean = false,
        val isBlank: Boolean = true,
    ) {
        val isValid: Boolean
            get() = !isBlank
    }

    data class DurationValidationState(
        val hasBeenChecked: Boolean = false,
        val isValidDuration: Boolean = true,
    ) {
        val isValid: Boolean
            get() = isValidDuration
    }

    sealed interface CreateLobbyEvent {
        data object Success : CreateLobbyEvent
        data object InvalidFields : CreateLobbyEvent
        data class Error(val message: String?) : CreateLobbyEvent
    }
}