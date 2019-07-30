package fr.skyle.escapy.ext

import android.util.TypedValue
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorCompat

fun View.animateCompat(): ViewPropertyAnimatorCompat {
    return ViewCompat.animate(this)
}

fun View.hide() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.goneWithAnimation() {
    animateCompat().alpha(0f).setDuration(150).withEndAction {
        gone()
    }
}

fun View.hideWithAnimation() {
    animateCompat().alpha(0f).setDuration(150).withEndAction {
        hide()
    }
}

fun View.showWithAnimation() {
    animateCompat().alpha(1f).setDuration(150).withStartAction {
        show()
    }
}

fun View.dip(value: Float, type: Int = TypedValue.COMPLEX_UNIT_DIP): Float {
    return context.dip(value, type)
}