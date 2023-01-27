package com.android.todozen.edittasklist

import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseState

data class EditTaskListState(
    val id: Long? = null,
    val list: TaskList = TaskList(),
): BaseState
