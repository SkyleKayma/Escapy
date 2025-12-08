package fr.skyle.escapy.ui.screens.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.usecase.firebaseAuth.SignOutUseCase
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.utils.FirebaseAuthManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val firebaseAuthManager: FirebaseAuthManager,
    private val watchCurrentUserUseCase: WatchCurrentUserUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<State>(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        viewModelScope.launch {
            combine(
                firebaseAuthManager.state.map { it.user },
                watchCurrentUserUseCase()
            ) { firebaseUser, user ->
                _state.update {
                    it.copy(
                        username = user?.username,
                        email = firebaseUser?.email,
                        createdAt = user?.createdAt,
                        avatar = Avatar.fromType(user?.avatarType),
                        authProvider = firebaseUser?.authProvider ?: AuthProvider.ANONYMOUS
                    )
                }
            }.collect()
        }
    }

    fun signOut() {
        signOutUseCase()

        _state.update {
            it.copy(
                event = ProfileEvent.SignOutSuccess
            )
        }
    }

    fun eventDelivered() {
        _state.update {
            it.copy(event = null)
        }
    }

    data class State(
        val username: String? = null,
        val email: String? = null,
        val createdAt: Long? = null,
        val avatar: Avatar? = null,
        val authProvider: AuthProvider = AuthProvider.ANONYMOUS,
        val event: ProfileEvent? = null,
    )

    sealed interface ProfileEvent {
        data object SignOutSuccess : ProfileEvent
    }
}