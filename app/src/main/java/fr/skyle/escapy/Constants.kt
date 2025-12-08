package fr.skyle.escapy

import kotlin.time.Duration.Companion.milliseconds

// Global

val MIN_DELAY_TO_SHOW_LOADER = 250L.milliseconds
val MIN_DELAY_BEFORE_SHOWING_SCREEN_LOADER = 300.milliseconds
val MIN_DELAY_BEFORE_SHOWING_LOADER = 100.milliseconds

// Password rules

const val PASSWORD_VALID_LENGTH = 8

// Lobby

const val JOIN_LOBBY_QRCODE_REGEX = "^lobby:([A-Za-z0-9_-]+);pwd:(.*)$"