package eu.hagisoft.imgmagic.utils

import android.view.View
import android.widget.ViewAnimator
import androidx.databinding.BindingAdapter

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun ViewAnimator.displayChildIfNecessary(child: Int) {
    if (displayedChild != child) {
        displayedChild = child
    }
}