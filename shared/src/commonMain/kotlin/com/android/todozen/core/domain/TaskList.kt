package com.android.todozen.core.domain

data class TaskList(
    val id: Long? = null,
    val title: String = "",
): ListItem {
    override fun getUuid(): Any? = id
}
