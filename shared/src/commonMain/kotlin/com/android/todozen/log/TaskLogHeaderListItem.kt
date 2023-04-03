package com.android.todozen.log

data class TaskLogHeaderListItem(
    val header: String,
): TaskLogItem {
    override fun getUuid(): Any = toString()
}