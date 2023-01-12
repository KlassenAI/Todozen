package com.android.todozen.features.tasklist

import com.android.todozen.domain.Task

data class TaskListState(
    val tasks: List<Task> = emptyList(),
    val doneTasks: List<Task> = emptyList()
)