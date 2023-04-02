package com.android.todozen.features.actionlog

import com.android.todozen.core.domain.toLocalDateTime
import database.TaskLogEntity

fun TaskLogEntity.map() = TaskLog(
    id = taskLogId,
    type = TaskLogType.valueOf(taskLogType),
    date = date.toLocalDateTime(),
    taskId = taskId,
    taskTitle = taskTitle,
    taskCategoryId = taskCategoryId,
)