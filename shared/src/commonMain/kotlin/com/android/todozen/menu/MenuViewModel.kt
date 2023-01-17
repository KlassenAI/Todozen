package com.android.todozen.menu

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

class MenuViewModel(
    private val taskListDS: TaskListDataSource
) : BaseViewModel<MenuState>() {

    override fun initialState() = MenuState()

    init {
        loadTaskLists()
    }

    fun loadTaskLists() {
        viewModelScope.launch {
            taskListDS.getFlowTaskLists().collect {
                updateState { copy(taskLists = listOf(TaskList(null, "Входящие")) + it) }
            }
        }
    }

    fun deleteTaskList(taskList: TaskList) {
        viewModelScope.launch { taskListDS.deleteTaskList(taskList.id!!) }
    }
}