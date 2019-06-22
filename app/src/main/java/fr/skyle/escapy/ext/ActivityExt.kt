package fr.skyle.escapy.ext

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


inline fun <reified T : Activity> Activity.startActivityForResult(
    requestCode: Int,
    block: Intent.() -> Unit = {}
) {
    startActivityForResult(Intent(this, T::class.java).apply {
        block(this)
    }, requestCode)
}

fun Activity.hideKeyboard(needToBeShown: Boolean? = true) {
    val focused = currentFocus ?: return
    focused.clearFocus()
    focused.windowToken?.let { inputMethodManager.hideSoftInputFromWindow(it, 0) }
}

fun Activity.showKeyboard(needToBeShown: Boolean? = true) {
    val focused = currentFocus ?: return
    focused.clearFocus()
    inputMethodManager.showSoftInput(focused, 0)
}

fun Activity.snackbar(str: String, @BaseTransientBottomBar.Duration duration: Int) {
    Snackbar.make(window.decorView.rootView, str, duration).show()
}

fun Activity.snackbar(@StringRes id: Int, @BaseTransientBottomBar.Duration duration: Int) {
    Snackbar.make(window.decorView.rootView, id, duration).show()
}

fun Activity.toast(str: String, @BaseTransientBottomBar.Duration duration: Int) {
    Toast.makeText(applicationContext, str, duration).show()
}

fun Activity.toast(@StringRes id: Int, @BaseTransientBottomBar.Duration duration: Int) {
    Toast.makeText(applicationContext, id, duration).show()
}