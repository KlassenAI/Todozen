package com.android.todozen.tasklist

import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseState

data class TaskListState(
    val id: Long? = null,
    val tasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList()
) : BaseState