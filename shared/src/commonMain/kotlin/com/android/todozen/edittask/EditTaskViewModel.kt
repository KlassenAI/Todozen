package com.android.todozen.edittask

import com.android.todozen.core.domain.Task
import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditTaskViewModel(
    private val taskDS: TaskDataSource,
    private val taskListDS: TaskListDataSource,
) : BaseViewModel<EditTaskState>() {

    override fun initialState() = EditTaskState()

    fun updateTitle(title: String) {
        if (title == state.value.title) return
        updateState { copy(title = title) }
    }

    fun updateDateTime(date: LocalDate?, time: LocalTime?) {
        updateState { copy(date = date, time = time) }
    }

    fun updateTaskList(listId: Long?, listTitle: String) {
        updateState { copy(listId = listId, listTitle = listTitle) }
    }

    fun updateInMyDay() {
        updateState { copy(inMyDay = inMyDay.not()) }
    }

    fun loadTask(taskId: Long?) {
        doJob {
            val task = taskId?.let { taskDS.getTask(it) } ?: Task()
            updateState {
                copy(
                    id = task.id,
                    title = task.title,
                    date = task.date,
                    time = task.time,
                    listId = task.listId,
                    listTitle = task.listTitle,
                    inMyDay = task.inMyDay
                )
            }
        }
    }

    fun editTask() {
        val state = _state.value
        val task = Task(
            id = state.id, title = state.title, date = state.date, time = state.time,
            listId = state.listId, listTitle = state.listTitle, inMyDay = state.inMyDay
        )
        doJob { taskDS.editTask(task) }
        clearState()
    }
}