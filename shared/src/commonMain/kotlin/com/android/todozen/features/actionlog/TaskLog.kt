package com.android.todozen.features.actionlog

import com.android.todozen.core.domain.ListItem
import kotlinx.datetime.LocalDateTime

data class TaskLog(
    val id: Long,
    val type: TaskLogType,
    val date: LocalDateTime,
    val taskId: Long,
    val taskTitle: String,
    val taskCategoryId: Long?,
): ListItem {
    override fun getUuid(): Any = id
}