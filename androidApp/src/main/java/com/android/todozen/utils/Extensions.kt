package com.android.todozen.utils

import android.app.Activity
import android.widget.Toast
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