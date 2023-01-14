package com.android.todozen.features.core

import android.app.Activity
import android.widget.EditText
import android.widget.Toast
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

var EditText.str: String
    set(value) {
        setText(value)
        setSelection(value.length)
    }
    get() = text.toString()

fun Fragment.showDialog(dialog: DialogFragment) {
    dialog.show(childFragmentManager, dialog::class.simpleName)
}
