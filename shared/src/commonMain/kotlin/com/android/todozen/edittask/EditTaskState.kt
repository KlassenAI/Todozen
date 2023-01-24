package com.android.todozen.edittask

import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditTaskState(
    val id: Long? = null,
    val task: Task = Task(),
): BaseState