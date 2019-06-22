package fr.skyle.escapy.ext

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar


/**
 * Created by Openium on 20/03/2018.
 */

val Fragment.appCompatActivity: AppCompatActivity?
    get() {
        activity?.let {
            return it as AppCompatActivity
        }
        return null
    }

inline fun <reified T : Activity> Fragment.startActivity(block: Intent.() -> Unit = {}) {
    requireActivity().startActivity(Intent(requireActivity(), T::class.java).apply {
        block(this)
    })
}

inline fun <reified T : Activity> Fragment.startActivityForResult(
    bundle: Bundle? = null,
    requestCode: Int
) {
    startActivityForResult(Intent(context, T::class.java).apply {
        bundle?.let {
            putExtras(bundle)
        }
    }, requestCode)
}

fun Fragment.snackbar(str: String, @BaseTransientBottomBar.Duration duration: Int) {
    view?.let {
        Snackbar.make(it, str, duration).show()
    }
}

fun Fragment.snackbar(@StringRes id: Int, @BaseTransientBottomBar.Duration duration: Int) {
    view?.let {
        Snackbar.make(it, id, duration).show()
    }
}

fun Fragment.toast(str: String, @BaseTransientBottomBar.Duration duration: Int) {
    Toast.makeText(context, str, duration).show()
}

fun Fragment.toast(@StringRes id: Int, @BaseTransientBottomBar.Duration duration: Int) {
    Toast.makeText(context, id, duration).show()
}

fun Fragment.setTitle(title: String) {
    appCompatActivity?.supportActionBar?.title = title
}

fun Fragment.setTitle(@StringRes title: Int) {
    appCompatActivity?.supportActionBar?.setTitle(title)
}

fun Fragment.dip(value: Int, type: Int = TypedValue.COMPLEX_UNIT_DIP): Int {
    return dip(value.toFloat(), type)
}

fun Fragment.dip(value: Float, type: Int = TypedValue.COMPLEX_UNIT_DIP): Int {
    return requireContext().dip(value, type).toInt()
}