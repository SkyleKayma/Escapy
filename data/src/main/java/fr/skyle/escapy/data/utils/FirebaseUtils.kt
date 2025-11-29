package fr.skyle.escapy.data.utils

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.utils.model.FirebaseResponse
import kotlinx.coroutines.suspendCancellableCoroutine

fun AuthResult.requireUser(): FirebaseUser =
    requireNotNull(user) { "Firebase user is null" }

fun FirebaseUser.getAuthProvider(): AuthProvider? {
    // Exclude the first element: it is ALWAYS FirebaseAuthProvider.PROVIDER_ID
    val realProvider = providerData
        .map { it.providerId }
        .firstOrNull { it != FirebaseAuthProvider.PROVIDER_ID }

    return AuthProvider.fromProviderId(realProvider)
}

suspend fun <T> DatabaseReference.readOnce(clazz: Class<T>): FirebaseResponse<T> {
    return suspendCancellableCoroutine { continuation ->
        this.get().addOnCompleteListener { task ->
            if (continuation.isCancelled) return@addOnCompleteListener

            if (task.isSuccessful) {
                val value = task.result.getValue(clazz)
                continuation.resume(
                    FirebaseResponse(
                        body = value,
                        exception = null
                    ),
                    null
                )
            } else {
                continuation.resume(
                    FirebaseResponse(
                        body = null,
                        exception = task.exception ?: Exception("Unknown Firebase error")
                    ),
                    null
                )
            }
        }
    }
}

suspend fun DatabaseReference.updateOnce(
    data: Map<String, Any?>
): FirebaseResponse<Unit> {
    return suspendCancellableCoroutine { continuation ->
        this.updateChildren(data)
            .addOnCompleteListener { task ->
                if (continuation.isCancelled) return@addOnCompleteListener

                if (task.isSuccessful) {
                    continuation.resume(
                        FirebaseResponse(
                            body = Unit,
                            exception = null
                        ),
                        null
                    )
                } else {
                    continuation.resume(
                        FirebaseResponse(
                            body = null,
                            exception = task.exception ?: Exception("Unknown Firebase error")
                        ),
                        null
                    )
                }
            }
    }
}