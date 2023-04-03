package com.android.todozen.core.domain

import com.android.todozen.core.expect.getName

data class InternalList(
    val type: InternalListType,
    var position: Int,
    override var sort: Sort,
    var taskCount: Int,
): TaskList(), ListItem {

    override fun getUuid() = type.id

    override var title: String = type.getName()
}