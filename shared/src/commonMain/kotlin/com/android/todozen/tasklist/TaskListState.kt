package com.android.todozen.tasklist

import com.android.todozen.core.domain.*
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate

data class TaskListState(
    val list: TaskList? = null,
    val tasks: List<Task> = emptyList(),
) : BaseState {

    val lists: List<EditableList> =
        tasks.filter { it.isDone.not() }.map { it.list }.distinct().sortedBy { it.position }
    val priorities: List<Priority> =
        tasks.filter { it.isDone.not() }.map { it.priority }.distinct().sortedBy { it.type.id }

    val currentTasksByTitle: List<Task>
        get() = tasks.filter { it.isDone.not() }.sortedBy { it.title }
    val outdatedTasks: List<Task> get() = tasks.filter { it.isDone.not() && it.date?.isOutdated() ?: false }
    val currentTasks: List<Task>
        get() = tasks.filter {
            it.isDone.not() && it.date?.isOutdated()?.not() ?: true
        }
    val doneTasks: List<Task> get() = tasks.filter { it.isDone }

    fun getTasksByList(listId: Long?): List<Task> = tasks.filter {
        it.list.id == listId && it.isDone.not()
    }

    fun getTasksByPriority(priority: Priority): List<Task> = tasks.filter {
        it.priority.type.id == priority.type.id && it.isDone.not()
    }

    private fun LocalDate.isOutdated(): Boolean = this < DateTimeUtil.today()
}