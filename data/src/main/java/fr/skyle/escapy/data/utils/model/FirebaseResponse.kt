package fr.skyle.escapy.data.utils.model

data class FirebaseResponse<T>(
    val body: T?,
    val exception: Exception?
) {
    val isSuccessful: Boolean
        get() = exception == null

    fun message(): String =
        exception?.message ?: ""
}