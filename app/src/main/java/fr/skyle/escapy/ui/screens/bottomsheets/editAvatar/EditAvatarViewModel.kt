package fr.skyle.escapy.ui.screens.bottomsheets.editAvatar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class EditAvatarViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _editAvatarState = MutableStateFlow<EditAvatarState>(EditAvatarState())
    val editAvatarState: StateFlow<EditAvatarState> by lazy { _editAvatarState.asStateFlow() }

    init {
        viewModelScope.launch {
            userRepository.watchCurrentUser()
                .filterNotNull()
                .collectLatest { currentUser ->
                    _editAvatarState.update {
                        it.copy(
                            currentAvatar = Avatar.fromType(currentUser.avatarType),
                        )
                    }
                }
        }
    }

    fun updateAvatar(avatar: Avatar) {
        viewModelScope.launch {
            _editAvatarState.update {
                it.copy(isLoading = true)
            }

            try {
                userRepository.updateAvatar(avatar).getOrThrow()
                userRepository.fetchCurrentUser().getOrThrow()

                _editAvatarState.update {
                    it.copy(event = EditAvatarEvent.Success)
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Timber.e(e)
                _editAvatarState.update {
                    it.copy(event = EditAvatarEvent.Error(e.message))
                }
            } finally {
                _editAvatarState.update {
                    it.copy(isLoading = false)
                }
            }
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _editAvatarState.update {
                it.copy(event = null)
            }
        }
    }

    data class EditAvatarState(
        val isLoading: Boolean? = null,
        val currentAvatar: Avatar? = null,
        val event: EditAvatarEvent? = null
    )

    sealed interface EditAvatarEvent {
        data object Success : EditAvatarEvent
        data class Error(val message: String?) : EditAvatarEvent
    }
}