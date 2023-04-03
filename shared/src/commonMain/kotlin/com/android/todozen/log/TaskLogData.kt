package com.android.todozen.log

import kotlinx.datetime.LocalDateTime

data class TaskLogData(
    val type: TaskLogType,
    val date: LocalDateTime,
    val taskId: Long,
    val taskTitle: String,
    val taskCategoryId: Long?,
)