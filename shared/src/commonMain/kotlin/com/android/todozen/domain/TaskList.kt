package com.android.todozen.domain

import com.android.todozen.utils.ListItem

data class TaskList(
    val id: Long? = null,
    val title: String = "",
): ListItem {
    override fun getUuid(): Any? = id
}
