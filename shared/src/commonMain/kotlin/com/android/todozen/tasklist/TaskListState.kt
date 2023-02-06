package com.android.todozen.tasklist

import com.android.todozen.core.domain.DateTimeUtil
import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate

data class TaskListState(
    val id: Long? = null,
    val tasks: List<Task> = emptyList()
) : BaseState {
    val outdatedTasks: List<Task> get() = tasks.filter { it.isDone.not() && it.date?.isOutdated() ?: false }
    val currentTasks: List<Task> get() = tasks.filter { it.isDone.not() && it.date?.isOutdated()?.not() ?: true }
    val doneTasks: List<Task> get() = tasks.filter { it.isDone }

    fun getTasksByList(listId: Long): List<Task> = tasks.filter { it.list.id == listId }

    private fun LocalDate.isOutdated(): Boolean = this < DateTimeUtil.today()
}