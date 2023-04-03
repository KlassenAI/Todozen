package com.android.todozen.action

data class TaskActionData(
    val type: TaskActionType,
    val taskId: Long,
    val taskTitle: String,
    val points: Long,
)
