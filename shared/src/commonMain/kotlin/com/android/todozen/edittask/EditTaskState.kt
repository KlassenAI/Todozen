package com.android.todozen.edittask

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.Task
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditTaskState(
    val id: Long? = null,
    val task: Task = Task(),
    val list: TaskList? = null,
    val isListPicked: Boolean = false
) : BaseState {

    val isUpdating: Boolean get() = id != null

    val listTitle: String?
        get() {
            return if (isListPicked) {
                task.list?.title
            } else if (list is EditableList) {
                list.title
            } else {
                null
            }
        }

    val trueList: EditableList?
        get() {
            return if (isListPicked) {
                task.list
            } else if (list is EditableList) {
                list
            } else {
                null
            }
        }
}