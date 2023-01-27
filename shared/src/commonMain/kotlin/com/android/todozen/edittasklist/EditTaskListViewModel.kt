package com.android.todozen.edittasklist

import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseViewModel

class EditTaskListViewModel(
    private val taskDS: TaskListDataSource
) : BaseViewModel<EditTaskListState>() {

    override fun initialState() = EditTaskListState()

    fun updateTitle(title: String) {
        if (title == state.value.list.title) return
        state { copy(list = list.apply { this.title = title }) }
    }

    fun updateColor(color: Int) = state { copy(list = list.apply { this.color = color }) }

    fun clearColor() = state { copy(list = list.apply { color = null }) }

    fun updateFavorite() = state { copy(list = list.apply { isFavorite = isFavorite.not() }) }

    fun loadTaskList(taskListId: Long?) {
        action {
            val list = taskListId?.let { taskDS.getTaskList(it) } ?: TaskList()
            state { copy(id = taskListId, list = list) }
        }
    }

    fun editTaskList() {
        action {
            taskDS.editTaskList(_state.value.list)
            clearState()
        }
    }
}