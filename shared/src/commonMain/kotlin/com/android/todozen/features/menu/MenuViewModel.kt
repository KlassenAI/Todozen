package com.android.todozen.features.menu

import com.android.todozen.data.TaskListDataSource
import com.android.todozen.domain.TaskList
import com.android.todozen.utils.BaseViewModel
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
            taskListDS.getAllTaskLists().collect {
                updateState { copy(taskLists = it) }
            }
        }
    }

    fun deleteTaskList(taskList: TaskList) {
        viewModelScope.launch { taskListDS.deleteTaskList(taskList.id!!) }
    }
}