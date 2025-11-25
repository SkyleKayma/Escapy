package fr.skyle.escapy.data.service

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import fr.skyle.escapy.data.utils.AuthenticatorHandler
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLifecycleObserver @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val authenticatorHandler: AuthenticatorHandler
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        firebaseAuth.currentUser?.reload()?.addOnSuccessListener {
            Timber.d("User reloaded after auth state change")
        }?.addOnFailureListener { e ->
            if (e is FirebaseAuthInvalidUserException) {
                Timber.e("Session expired: User must re-authenticate")
                authenticatorHandler.onNotAuthenticatedException()
            } else {
                Timber.e(e, "Failed to reload user session")
            }
        }
    }
}