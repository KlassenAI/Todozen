package com.android.todozen.menu

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseViewModel

class MenuViewModel(
    private val taskListDS: TaskListDataSource
) : BaseViewModel<MenuState>() {

    override fun initialState() = MenuState()

    init {
        loadTaskLists()
    }

    fun loadTaskLists() {
        action {
            val taskLists = taskListDS.getTaskLists()
            state { copy(taskLists = taskLists) }
        }
    }

    fun deleteTaskList(taskList: TaskList) {
        action {
            taskListDS.deleteTaskList(taskList)
            loadTaskLists()
        }
    }

    fun swapTaskLists(from: Int, to: Int) {
        val taskLists = state.value.taskLists
        val first = taskLists.first { it.position == from.toLong() }
        val second = taskLists.first { it.position == to.toLong() }
        first.position = second.position.also { second.position = first.position }
        action { taskListDS.updateTaskLists(taskLists) }
    }
}