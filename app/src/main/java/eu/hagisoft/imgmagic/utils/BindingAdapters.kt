package eu.hagisoft.imgmagic.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visible")
fun viewVisible(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}