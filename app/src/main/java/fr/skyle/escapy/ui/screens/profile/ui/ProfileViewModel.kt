package fr.skyle.escapy.ui.screens.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.repository.user.api.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState())
    val profileState: StateFlow<ProfileState> = _profileState.asStateFlow()

    init {
        viewModelScope.launch {
            userRepository.watchCurrentUser().filterNotNull().collect { currentUser ->
                _profileState.update {
                    it.copy(
                        userName = currentUser.name,
                        createdAt = currentUser.createdAt,
                        avatar = Avatar.fromType(currentUser.avatarType),
                        authProvider = userRepository.getAuthProvider()
                    )
                }
            }
        }
    }

    fun signOut() {
        userRepository.signOut()

        _profileState.update {
            it.copy(
                event = ProfileEvent.SignOutSuccess
            )
        }
    }

    fun eventDelivered() {
        viewModelScope.launch {
            _profileState.update {
                it.copy(event = null)
            }
        }
    }

    data class ProfileState(
        val userName: String? = null,
        val createdAt: Long? = null,
        val avatar: Avatar? = null,
        val authProvider: AuthProvider = AuthProvider.ANONYMOUS,
        val event: ProfileEvent? = null,
    )

    sealed interface ProfileEvent {
        data object SignOutSuccess : ProfileEvent
    }
}