package com.android.todozen.log

data class TaskLogListItem(
    val id: Long,
    val type: TaskLogType,
    val time: String,
    val taskId: Long,
    val taskTitle: String,
    val taskCategoryId: Long?,
): TaskLogItem {
    override fun getUuid(): Any = id
}