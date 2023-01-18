package com.android.todozen.edittasklist

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.presentation.BaseViewModel

class EditTaskListViewModel(
    private val taskDS: TaskListDataSource
) : BaseViewModel<EditTaskListState>() {

    override fun initialState() = EditTaskListState()

    fun updateTitle(title: String) {
        if (title == state.value.title) return
        updateState { copy(title = title) }
    }

    fun loadTaskList(taskListId: Long?) {
        doJob {
            val taskList = taskListId?.let { taskDS.getTaskList(it) }
            updateState { EditTaskListState.getFromTaskList(taskList) }
        }
    }

    fun editTaskList() {
        val taskList = _state.value.getTaskList()
        doJob {
            taskDS.editTaskList(taskList)
            clearState()
        }
    }
}