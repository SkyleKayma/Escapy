package fr.skyle.escapy.data.utils

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import fr.skyle.escapy.data.enums.AuthProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAuthHelper @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    // Helpful methods

    fun getCurrentUser(): FirebaseUser? =
        firebaseAuth.currentUser

    fun requireCurrentUser(): FirebaseUser =
        firebaseAuth.currentUser ?: throw Exception("No current user")

    fun isUserLoggedIn(): Boolean =
        getCurrentUser() != null

    fun getAccountEmail(): String? =
        getCurrentUser()?.email

    fun getAccountAuthProvider(): AuthProvider =
        firebaseAuth.currentUser?.getAuthProvider() ?: AuthProvider.ANONYMOUS

    // Other

    fun signInWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInAnonymously(): Task<AuthResult> =
        firebaseAuth.signInAnonymously()

    fun signOut() {
        firebaseAuth.signOut()
    }
}