package com.android.todozen.menu

import co.touchlab.kermit.Logger
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
            taskListDS.deleteTaskList(taskList.id!!)
            loadTaskLists()
        }
    }

    fun swapTaskLists(from: Int, to: Int) {
        val taskLists = state.value.taskLists.toMutableList()
        taskLists[from].position = taskLists[to].position.also { taskLists[to].position = taskLists[from].position }
        taskLists.swap(from, to)
        action { taskListDS.updateTaskLists(taskLists[from], taskLists[to]) }
    }

    private fun <T> MutableList<T>.swap(i: Int, j: Int) {
        val t = this[i]
        this[i] = this[j]
        this[j] = t
    }
}