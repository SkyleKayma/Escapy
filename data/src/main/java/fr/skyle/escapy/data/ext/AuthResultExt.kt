package fr.skyle.escapy.data.ext

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

fun AuthResult.requireUser(): FirebaseUser =
    requireNotNull(user) { "Firebase returned success but user was null" }
