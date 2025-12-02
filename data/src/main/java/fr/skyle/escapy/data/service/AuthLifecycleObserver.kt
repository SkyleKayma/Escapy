package fr.skyle.escapy.data.service

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLifecycleObserver @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : DefaultLifecycleObserver {

    override fun onResume(owner: LifecycleOwner) {
        firebaseAuth.currentUser?.reload()
    }
}