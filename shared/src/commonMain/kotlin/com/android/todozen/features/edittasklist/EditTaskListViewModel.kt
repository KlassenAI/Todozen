package com.android.todozen.features.edittasklist

import com.android.todozen.core.data.ListDataSource
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.presentation.BaseViewModel

class EditTaskListViewModel(
    private val taskDS: ListDataSource
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
            val taskList: EditableList
            if (taskListId == null) {
                taskList = EditableList(position = taskDS.getEditableListsCount())
            } else {
                taskList = taskDS.getEditableList(taskListId)
            }
            state { copy(id = taskListId, taskList = taskList) }
        }
    }

    fun editTaskList() {
        action {
            if (_state.value.id == null) {
                taskDS.insertEditableList(_state.value.taskList)
            } else {
                taskDS.updateEditableList(_state.value.taskList)
            }
            clearState()
            loadTaskList(null)
        }
    }
}