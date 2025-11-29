package fr.skyle.escapy.data

import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

// Json

internal val json by lazy {
    Json {
        ignoreUnknownKeys = true
        encodeDefaults = false
    }
}

// User

const val PREFIX_GUEST = "Guest"
const val PREFIX_PLAYER = "Player"

// Firebase

val DEFAULT_API_TIMEOUT = 10.seconds