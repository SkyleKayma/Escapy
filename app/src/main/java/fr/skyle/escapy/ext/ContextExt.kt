package fr.skyle.escapy.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import timber.log.Timber


val Context.hasNetwork: Boolean
    get() {
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo?.isConnected == true
    }

inline fun <reified T : Activity> Context.startActivity(block: Intent.() -> Unit = {}) {
    startActivity(Intent(this, T::class.java).apply {
        block(this)
    })
}

val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

fun Context.dip(value: Float, type: Int = TypedValue.COMPLEX_UNIT_DIP): Float {
    return TypedValue.applyDimension(type, value, resources.displayMetrics)
}

fun Context.getDrawableCompat(@DrawableRes resId: Int): Drawable? = AppCompatResources.getDrawable(this, resId)

fun Context.getColorCompat(@ColorRes resId: Int): Int = ContextCompat.getColor(this, resId)

fun Context.getFontCompat(@FontRes fontId: Int): Typeface? = ResourcesCompat.getFont(this, fontId)

fun Context.appVersion(): String {
    var version = ""
    try {
        val packageInfo = packageManager?.getPackageInfo(packageName, 0)
        packageInfo?.let {
            version = String.format("%s (%d)", packageInfo.versionName, packageInfo.versionCode)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        Timber.e(e)
    }
    return version
}