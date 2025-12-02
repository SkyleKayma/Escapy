package fr.skyle.escapy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.utils.FirebaseAuthManager
import fr.skyle.escapy.data.utils.model.FirebaseConnectionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuthManager: FirebaseAuthManager,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> by lazy { _state.asStateFlow() }

    init {
        viewModelScope.launch {
            _state.update {
                it.copy(isUserLoggedIn = firebaseAuthManager.state.firstOrNull()?.user != null)
            }
        }

        viewModelScope.launch {
            firebaseAuthManager.state
                .filter { it.connectionState == FirebaseConnectionState.EXPIRED }
                .collectLatest {
                    _state.update {
                        it.copy(event = MainEvent.LogoutEvent)
                    }
                }
        }
    }

    fun authenticatorEventDelivered() {
        viewModelScope.launch {
            _state.update {
                it.copy(event = null)
            }
        }
    }

    data class State(
        val isUserLoggedIn: Boolean = false,
        val event: MainEvent? = null
    )

    sealed interface MainEvent {
        data object LogoutEvent : MainEvent
    }
}