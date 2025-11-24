package fr.skyle.escapy.data.ext

import com.google.android.gms.tasks.Task
import fr.skyle.escapy.data.DEFAULT_API_TIMEOUT
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

suspend fun <T> Task<T>.awaitWithTimeout(timeout: Duration = DEFAULT_API_TIMEOUT): T =
    withTimeout(timeout) {
        await()
    }