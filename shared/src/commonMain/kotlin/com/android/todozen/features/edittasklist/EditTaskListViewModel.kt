package com.android.todozen.features.edittasklist

import com.android.todozen.data.TaskListDataSource
import com.android.todozen.utils.BaseViewModel
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