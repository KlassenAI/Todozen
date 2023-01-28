package com.android.todozen.edittasklist

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseViewModel

class EditTaskListViewModel(
    private val taskDS: TaskListDataSource
) : BaseViewModel<EditTaskListState>() {

    override fun initialState() = EditTaskListState()

    fun updateTitle(title: String) {
        if (title == state.value.taskList.title) return
        state { copy(taskList = taskList.apply { this.title = title }) }
    }

    fun updateColor(color: Int) = state { copy(taskList = taskList.apply { this.color = color }) }

    fun clearColor() = state { copy(taskList = taskList.apply { color = null }) }

    fun updateFavorite() = state { copy(taskList = taskList.apply { isFavorite = isFavorite.not() }) }

    fun loadTaskList(taskListId: Long?) {
        action {
            val taskList: TaskList
            if (taskListId == null) {
                taskList = TaskList(position = taskDS.getTaskListsCount())
            } else {
                taskList = taskDS.getTaskList(taskListId)
            }
            state { copy(id = taskListId, taskList = taskList) }
        }
    }

    fun editTaskList() {
        action {
            if (_state.value.id == null) {
                taskDS.insertTaskList(_state.value.taskList)
            } else {
                taskDS.updateTaskList(_state.value.taskList)
            }
            clearState()
            loadTaskList(null)
        }
    }
}