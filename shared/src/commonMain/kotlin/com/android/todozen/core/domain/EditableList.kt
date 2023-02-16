package com.android.todozen.core.domain

data class EditableList(
    var id: Long = 0,
    override var title: String = "",
    var isFavorite: Boolean = false,
    var color: Int? = null,
    var position: Long = 0,
    override var sort: Sort = Sort.DEFAULT
) : TaskList(), ListItem {

    override fun getUuid(): Any = id
}
