package fr.skyle.escapy.ui.screens.editAvatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.MIN_DELAY_BEFORE_SHOWING_LOADER
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.usecase.user.UpdateAvatarUseCase
import fr.skyle.escapy.data.usecase.user.UpdateAvatarUseCaseResponse
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditAvatarViewModel @Inject constructor(
    private val watchCurrentUserUseCase: WatchCurrentUserUseCase,
    private val updateAvatarUseCase: UpdateAvatarUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        viewModelScope.launch {
            watchCurrentUserUseCase()
                .filterNotNull()
                .collectLatest { user ->
                    _state.update {
                        it.copy(
                            currentAvatar = Avatar.fromType(user.avatarType),
                        )
                    }
                }
        }
    }

    fun updateAvatar(avatar: Avatar) {
        viewModelScope.launch {
            _state.update {
                it.copy(isAvatarUpdating = true)
            }

            // Start a delayed job to show the loading state only if api call is slow
            val showLoadingJob = launch {
                delay(MIN_DELAY_BEFORE_SHOWING_LOADER)
                _state.update {
                    it.copy(isLoadingShown = true)
                }
            }

            val response = updateAvatarUseCase(avatar)

            showLoadingJob.cancel()

            when (response) {
                is UpdateAvatarUseCaseResponse.Error -> {
                    _state.update {
                        it.copy(event = EditAvatarEvent.Error(response.message))
                    }
                }

                UpdateAvatarUseCaseResponse.Success -> {
                    _state.update {
                        it.copy(event = EditAvatarEvent.Success)
                    }
                }
            }

            _state.update {
                it.copy(
                    isAvatarUpdating = false,
                    isLoadingShown = false
                )
            }
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _state.update {
                it.copy(event = null)
            }
        }
    }

    data class State(
        val currentAvatar: Avatar? = null,
        val isAvatarUpdating: Boolean = false,
        val isLoadingShown: Boolean = false,
        val event: EditAvatarEvent? = null
    )

    sealed interface EditAvatarEvent {
        data object Success : EditAvatarEvent
        data class Error(val message: String?) : EditAvatarEvent
    }
}