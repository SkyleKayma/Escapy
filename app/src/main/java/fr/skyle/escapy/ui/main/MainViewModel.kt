package fr.skyle.escapy.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.skyle.escapy.data.utils.AuthenticatorHandler
import fr.skyle.escapy.data.utils.FirebaseAuthHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuthHelper: FirebaseAuthHelper,
    private val authenticatorHandler: AuthenticatorHandler,
) : ViewModel() {

    private val _mainState: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val mainState: StateFlow<MainState> by lazy { _mainState.asStateFlow() }

    init {
        viewModelScope.launch {
            authenticatorHandler.authenticatorEvent
                .collectLatest { event ->
                    _mainState.update {
                        it.copy(
                            authenticatorEvent = when (event) {
                                AuthenticatorHandler.AuthenticatorEvent.Logout -> {
                                    AuthenticatorEvent.LogoutEvent
                                }

                                null -> null
                            }
                        )
                    }
                }
        }
    }

    fun isUserLoggedIn(): Boolean =
        firebaseAuthHelper.isUserLoggedIn()

    fun authenticatorEventDelivered() {
        viewModelScope.launch {
            authenticatorHandler.eventDelivered()
        }
    }

    data class MainState(
        val authenticatorEvent: AuthenticatorEvent? = null
    )

    sealed interface AuthenticatorEvent {
        data object LogoutEvent : AuthenticatorEvent
    }
}