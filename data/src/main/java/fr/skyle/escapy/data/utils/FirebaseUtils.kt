package fr.skyle.escapy.data.utils

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthProvider
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.Query
import fr.skyle.escapy.data.enums.AuthProvider
import fr.skyle.escapy.data.ext.awaitWithTimeout
import timber.log.Timber

fun AuthResult.requireUser(): FirebaseUser =
    requireNotNull(user) { "Firebase user is null" }

fun FirebaseUser.getAuthProvider(): AuthProvider? {
    // Exclude the first element: it is ALWAYS FirebaseAuthProvider.PROVIDER_ID
    val realProvider = providerData
        .map { it.providerId }
        .firstOrNull { it != FirebaseAuthProvider.PROVIDER_ID }

    return AuthProvider.fromProviderId(realProvider)
}

suspend fun <T> Query.readOnce(clazz: Class<T>): T? {
    val path = this.toString()
    Timber.i("ℹ️ READ → path=$path")

    val snapshot = get().addOnFailureListener { e ->
        Timber.e(e, "🟥 READ → path=$path")
    }.awaitWithTimeout()

    val value = snapshot.getValue(clazz)
    Timber.i("🟩 READ → path=$path\ndata=$value")

    return value
}

suspend fun <T> Query.readOnce(typeIndicator: GenericTypeIndicator<T>): T? {
    val path = this.toString()
    Timber.i("ℹ️ READ → path=$path")

    val snapshot = get()
        .addOnFailureListener { e ->
            Timber.e(e, "🟥 READ → path=$path")
        }.awaitWithTimeout()

    val value = snapshot.getValue(typeIndicator)
    Timber.i("🟩 READ → path=$path\ndata=$value")

    return value
}

// ---------------- WRITE ----------------

suspend fun DatabaseReference.writeOnce(data: Any) {
    val path = this.toString()
    Timber.i("ℹ️ WRITE → path=$path\ndata=$data")

    setValue(data)
        .addOnFailureListener { e ->
            Timber.e(e, "🟥 WRITE → path=$path")
        }
        .awaitWithTimeout()

    Timber.i("🟩 WRITE → path=$path")
}

// ---------------- UPDATE ----------------

suspend fun DatabaseReference.updateOnce(data: Map<String, Any?>) {
    val path = this.toString()
    Timber.i("ℹ️ UPDATE → path=$path\ndata=$data")

    updateChildren(data)
        .addOnFailureListener { e ->
            Timber.e(e, "🟥 UPDATE → path=$path")
        }
        .awaitWithTimeout()

    Timber.i("🟩 UPDATE → path=$path")
}