package fr.skyle.escapy.data.ext

import com.google.android.gms.tasks.Task
import fr.skyle.escapy.data.DEFAULT_TIMEOUT_MILLISECONDS
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout

suspend fun <T> Task<T>.awaitWithTimeout(timeoutMillis: Long = DEFAULT_TIMEOUT_MILLISECONDS): T =
    withTimeout(timeoutMillis) {
        await()
    }
