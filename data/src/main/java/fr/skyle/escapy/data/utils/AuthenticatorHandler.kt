package fr.skyle.escapy.data.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticatorHandler @Inject constructor() {
    private val _authenticatorEvent = MutableStateFlow<AuthenticatorEvent?>(null)
    val authenticatorEvent: StateFlow<AuthenticatorEvent?> by lazy { _authenticatorEvent.asStateFlow() }

    fun onNotAuthenticatedException() {
        _authenticatorEvent.tryEmit(AuthenticatorEvent.Logout)
    }

    fun eventDelivered() {
        _authenticatorEvent.tryEmit(null)
    }

    sealed interface AuthenticatorEvent {
        data object Logout : AuthenticatorEvent
    }
}