package com.android.todozen.core.domain

data class TaskList(
    var id: Long? = null,
    var title: String = "",
    var isFavorite: Boolean = false,
    var color: Int? = null,
    var position: Long = 0
) : ListItem {
    override fun getUuid(): Any? = id
}
