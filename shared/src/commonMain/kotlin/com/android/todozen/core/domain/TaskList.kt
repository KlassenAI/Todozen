package com.android.todozen.core.domain

data class TaskList(
    val id: Long? = null,
    var title: String = "",
    var isFavorite: Boolean = false,
    var color: Int? = null
) : ListItem {
    override fun getUuid(): Any? = id
}
