package fr.skyle.escapy.data.ext

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resumeWithException

fun AuthResult.requireUser(): FirebaseUser =
    requireNotNull(user) { "Firebase returned success but user was null" }

suspend fun <T> DatabaseReference.readOnce(clazz: Class<T>): T? {
    return suspendCancellableCoroutine { continuation ->
        this.get().addOnCompleteListener { task ->
            if (continuation.isCancelled) return@addOnCompleteListener

            if (task.isSuccessful) {
                val snapshot = task.result
                val value = snapshot.getValue(clazz)
                continuation.resume(value, null)
            } else {
                continuation.resumeWithException(
                    task.exception ?: Exception("Unknown Firebase error")
                )
            }
        }
    }
}
