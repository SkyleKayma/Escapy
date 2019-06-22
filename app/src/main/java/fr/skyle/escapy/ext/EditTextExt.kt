package fr.skyle.escapy.ext

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView

fun EditText.text(): String {
    return text?.toString() ?: ""
}

fun EditText.onActionDone(callback: () -> Unit) {
    setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_GO) {
            callback.invoke()
            return@OnEditorActionListener true
        }
        false
    })
}