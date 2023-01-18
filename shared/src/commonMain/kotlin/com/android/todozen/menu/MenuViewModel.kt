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

    private fun loadTaskLists() {
        doJob { taskListDS.getTaskLists().collect { updateState { copy(taskLists =  it) } } }
    }

    fun deleteTaskList(taskList: TaskList) {
        doJob { taskListDS.deleteTaskList(taskList.id!!) }
    }
}