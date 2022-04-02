package eu.hagisoft.imgmagic.utils

import android.view.View
import androidx.databinding.BindingAdapter

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}