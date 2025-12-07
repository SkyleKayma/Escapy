package fr.skyle.escapy.data.utils

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.utils.model.FirebaseResponse
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume

fun AuthResult.requireUser(): FirebaseUser =
    requireNotNull(user) { "Firebase user is null" }

fun FirebaseUser.getAuthProvider(): AuthProvider? {
    // Exclude the first element: it is ALWAYS FirebaseAuthProvider.PROVIDER_ID
    val realProvider = providerData
        .map { it.providerId }
        .firstOrNull { it != FirebaseAuthProvider.PROVIDER_ID }

    return AuthProvider.fromProviderId(realProvider)
}

suspend fun <T> Query.readOnce(clazz: Class<T>): FirebaseResponse<T> {
    val path = this.toString()
    Timber.i("ℹ️ READ → path=$path")

    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val value = task.result.getValue(clazz)
                Timber.i("🟩 READ → path=$path, value=$value")
                continuation.resume(FirebaseResponse(value, null))
            } else {
                val e = task.exception ?: Exception("Unknown Firebase error")
                Timber.e(e, "🟥 READ → path=$path")
                continuation.resume(FirebaseResponse(null, e))
            }
        }

        continuation.invokeOnCancellation {
            Timber.w("⬜ READ canceled → path=$path")
        }
    }
}

suspend fun <T> Query.readOnce(typeIndicator: GenericTypeIndicator<T>): FirebaseResponse<T> {
    val path = this.toString()
    Timber.i("ℹ️ READ → path=$path")

    return suspendCancellableCoroutine { continuation ->
        get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val value = task.result.getValue(typeIndicator)
                Timber.i("🟩 READ → path=$path, value=$value")
                continuation.resume(FirebaseResponse(value, null))
            } else {
                val e = task.exception ?: Exception("Unknown Firebase error")
                Timber.e(e, "🟥 READ → path=$path")
                continuation.resume(FirebaseResponse(null, e))
            }
        }

        continuation.invokeOnCancellation {
            Timber.w("⬜ READ canceled → path=$path")
        }
    }
}

// ---------------- WRITE ----------------

suspend fun DatabaseReference.writeOnce(data: Any): FirebaseResponse<Unit> {
    val path = this.toString()
    Timber.i("ℹ️ WRITE → path=$path, data=$data")

    return suspendCancellableCoroutine { continuation ->
        setValue(data).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.i("🟩 WRITE → path=$path")
                continuation.resume(FirebaseResponse(Unit, null))
            } else {
                val e = task.exception ?: Exception("Unknown Firebase error")
                Timber.e(e, "🟥 WRITE → path=$path")
                continuation.resume(FirebaseResponse(null, e))
            }
        }

        continuation.invokeOnCancellation {
            Timber.w("⬜ WRITE canceled → path=$path")
        }
    }
}

// ---------------- UPDATE ----------------

suspend fun DatabaseReference.updateOnce(data: Map<String, Any?>): FirebaseResponse<Unit> {
    val path = this.toString()
    Timber.i("ℹ️ UPDATE → path=$path, data=$data")

    return suspendCancellableCoroutine { continuation ->
        updateChildren(data).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.i("🟩 UPDATE → path=$path")
                continuation.resume(FirebaseResponse(Unit, null))
            } else {
                val e = task.exception ?: Exception("Unknown Firebase error")
                Timber.e(e, "🟥 UPDATE → path=$path")
                continuation.resume(FirebaseResponse(null, e))
            }
        }

        continuation.invokeOnCancellation {
            Timber.w("⬜ UPDATE canceled → path=$path")
        }
    }
}

suspend fun DatabaseReference.updateChildrenOnce(
    updates: Map<String, Any?>
): FirebaseResponse<Unit> {
    val path = this.toString()
    Timber.i("⬜ UPDATE_CHILDREN → path=$path, updates=$updates")

    return suspendCancellableCoroutine { continuation ->
        updateChildren(updates).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Timber.i("🟩 UPDATE_CHILDREN → path=$path")
                continuation.resume(FirebaseResponse(Unit, null))
            } else {
                val e = task.exception ?: Exception("Unknown Firebase error")
                Timber.e(e, "🟥 UPDATE_CHILDREN → path=$path")

                continuation.resume(FirebaseResponse(null, e))
            }
        }

        continuation.invokeOnCancellation {
            Timber.w("⬜ UPDATE_CHILDREN canceled → path=$path")
        }
    }
}