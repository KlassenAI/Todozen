package com.android.todozen.edittask

import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditTaskState(
    val id: Long? = null,
    val task: Task = Task(),
    val taskList: TaskList? = null,
    val isListPicked: Boolean = false,
    val isDatePicked: Boolean = false,
) : BaseState {

    val isUpdating: Boolean get() = id != null

    val list: EditableList?
        get() {
            return if (isListPicked) {
                task.list
            } else if (taskList is EditableList) {
                taskList
            } else {
                null
            }
        }

    val dateTime: Pair<LocalDate?, LocalTime?>
        get() {
            return if (isDatePicked) {
                task.date to task.time
            } else if (taskList is InternalList && taskList.type.isDateType()) {
                when (taskList.type) {
                    MY_DAY -> DateTimeUtil.today() to null
                    TOMORROW -> DateTimeUtil.tomorrow() to null
                    NEXT_WEEK -> DateTimeUtil.tomorrow() to null
                    else -> null to null
                }
            } else {
                null to null
            }
        }
}