package com.android.todozen.menu

import com.android.todozen.core.data.ListDataSource
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.presentation.BaseViewModel

class MenuViewModel(
    private val listDS: ListDataSource
) : BaseViewModel<MenuState>() {

    override fun initialState() = MenuState()

    init {
        loadInternalLists()
        loadEditableLists()
    }

    private fun loadInternalLists() {
        action {
            val lists = listDS.getInternalLists()
            state { copy(internalLists = lists) }
        }
    }

    fun loadEditableLists() {
        action {
            val taskLists = listDS.getEditableLists()
            state { copy(editableLists = taskLists) }
        }
    }

    fun deleteTaskList(taskList: EditableList) {
        action {
            listDS.deleteEditableList(taskList)
            loadEditableLists()
        }
    }

    fun swapTaskLists(from: Int, to: Int) {
        val taskLists = state.value.editableLists
        val first = taskLists.first { it.position == from.toLong() }
        val second = taskLists.first { it.position == to.toLong() }
        first.position = second.position.also { second.position = first.position }
        action { listDS.updateEditableLists(taskLists) }
    }
}