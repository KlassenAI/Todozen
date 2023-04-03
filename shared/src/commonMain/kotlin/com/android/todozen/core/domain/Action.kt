package com.android.todozen.core.domain

import com.android.todozen.action.TaskActionType
import com.android.todozen.task.Task

data class Action(
    val id: Long? = null,
    val points: Long,
    val type: TaskActionType,
    val task: Task?,
)
