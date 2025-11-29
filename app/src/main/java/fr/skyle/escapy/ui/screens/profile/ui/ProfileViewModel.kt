package fr.skyle.escapy.ui.screens.profile.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.enums.Avatar
import fr.skyle.escapy.data.usecase.account.SignOutUseCase
import fr.skyle.escapy.data.usecase.account.SignOutUseCaseResponse
import fr.skyle.escapy.data.usecase.user.WatchCurrentUserUseCase
import fr.skyle.escapy.data.utils.FirebaseAuthHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    firebaseAuthHelper: FirebaseAuthHelper,
    private val signOutUseCase: SignOutUseCase,
    private val watchCurrentUserUseCase: WatchCurrentUserUseCase
) : ViewModel() {

    private val _profileState = MutableStateFlow<ProfileState>(ProfileState())
    val profileState: StateFlow<ProfileState> by lazy { _profileState.asStateFlow() }

    init {
        viewModelScope.launch {
            watchCurrentUserUseCase()
                .map { it.user }
                .filterNotNull()
                .collectLatest { user ->
                    _profileState.update {
                        it.copy(
                            username = user.username,
                            email = firebaseAuthHelper.getAccountEmail(),
                            createdAt = user.createdAt,
                            avatar = Avatar.fromType(user.avatarType),
                            authProvider = firebaseAuthHelper.getAccountAuthProvider()
                        )
                    }
                }
        }
    }

    fun signOut() {
        when (signOutUseCase()) {
            SignOutUseCaseResponse.Success -> {
                _profileState.update {
                    it.copy(
                        event = ProfileEvent.SignOutSuccess
                    )
                }
            }
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