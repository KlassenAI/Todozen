package com.android.todozen.edittasklist

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

class EditTaskListViewModel(
    private val taskDS: TaskListDataSource
) : BaseViewModel<EditTaskListState>() {

    override fun initialState() = EditTaskListState()

    fun updateTitle(title: String) = updateState { copy(title = title) }

    fun loadTaskList(taskListId: Long?) {
        viewModelScope.launch {
            val taskList = taskListId?.let { taskDS.getTaskList(it) }
            updateState { EditTaskListState.getFromTaskList(taskList) }
        }
    }

    fun editTaskList() {
        val taskList = _state.value.getTaskList()
        viewModelScope.launch { taskDS.editTaskList(taskList) }
    }
}