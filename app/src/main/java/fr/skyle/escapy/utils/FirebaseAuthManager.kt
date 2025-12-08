package fr.skyle.escapy.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.skyle.escapy.data.utils.getAuthProvider
import fr.skyle.escapy.data.utils.model.FirebaseConnectionState
import fr.skyle.escapy.data.utils.model.FirebaseUserSnapshot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthManager @Inject constructor(
    firebaseAuth: FirebaseAuth,
) {
    private val _state = MutableStateFlow(
        FirebaseAuthState(
            user = firebaseAuth.currentUser.toSnapshot(),
            connectionState = initialState(firebaseAuth.currentUser)
        )
    )
    val state: StateFlow<FirebaseAuthState> = _state.asStateFlow()

    init {
        val authListener = FirebaseAuth.AuthStateListener { auth ->
            updateState(auth.currentUser)
        }

        val idTokenListener = FirebaseAuth.IdTokenListener { auth ->
            updateState(auth.currentUser)
        }

        firebaseAuth.addAuthStateListener(authListener)
        firebaseAuth.addIdTokenListener(idTokenListener)
    }

    private fun updateState(currentUser: FirebaseUser?) {
        val previousUser = _state.value.user
        val currentSnapshot = currentUser.toSnapshot()

        val newConnectionState = when {
            previousUser != null && currentSnapshot == null ->
                FirebaseConnectionState.EXPIRED

            currentSnapshot != null ->
                FirebaseConnectionState.CONNECTED

            else ->
                FirebaseConnectionState.DISCONNECTED
        }

        val newState = FirebaseAuthState(
            user = currentSnapshot,
            connectionState = newConnectionState
        )

        if (_state.value != newState) {
            Timber.Forest.d("Firebase auth state updated: $newState")
            _state.value = newState
        }
    }

    private fun initialState(user: FirebaseUser?) =
        if (user != null) {
            FirebaseConnectionState.CONNECTED
        } else {
            FirebaseConnectionState.DISCONNECTED
        }

    private fun FirebaseUser?.toSnapshot(): FirebaseUserSnapshot? =
        this?.let {
            FirebaseUserSnapshot(
                uid = uid,
                email = email,
                isEmailVerified = isEmailVerified,
                authProvider = getAuthProvider()
            )
        }

    data class FirebaseAuthState(
        val user: FirebaseUserSnapshot?,
        val connectionState: FirebaseConnectionState
    )
}