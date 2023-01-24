package com.android.todozen.core

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Activity.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun RecyclerView.initVertical(adapter: BaseAdapterDelegate) {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    this.adapter = adapter
}

// При сохранении состояния фрагментов декораторы сохраняются и отступы между элементами растут
private fun RecyclerView.clearDecorations() {
    for (i in 0 until itemDecorationCount) { removeItemDecorationAt(i) }
}

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(childFragmentManager, dialog::class.simpleName)
}

fun View.visible(visible: Boolean) {
    isVisible = visible
}

inline fun <V : View, P> V.init(params: P?, crossinline initializer: V.(params: P) -> Unit) {
    visible(params != null)
    params?.let { initializer(this, it) }
}

fun Fragment.getColor(@ColorRes id: Int) = ContextCompat.getColor(requireContext(), id)

fun Fragment.getColorList(@ColorRes id: Int) = ContextCompat.getColorStateList(requireContext(), id)

fun ImageView.setImage(@DrawableRes id: Int) {
    setImageResource(id)
}
