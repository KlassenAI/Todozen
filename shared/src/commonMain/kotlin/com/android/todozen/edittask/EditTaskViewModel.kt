package com.android.todozen.edittask

import com.android.todozen.core.domain.Task
import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditTaskViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<EditTaskState>() {

    override fun initialState() = EditTaskState()

    fun updateTitle(title: String) {
        if (title == state.value.task.title) return
        state { copy(task = task.apply { this.title = title }) }
    }

    fun updateDateTime(date: LocalDate?, time: LocalTime?) {
        state { copy(task = task.apply { this.date = date; this.time = time }) }
    }

    fun updateTaskList(listId: Long?, listTitle: String) {
        state { copy(task = task.apply { this.listId = listId; this.listTitle = listTitle }) }
    }

    fun updateInMyDay() = state { copy(task = task.apply { isInMyDay = isInMyDay.not() }) }

    fun updateFavorite() = state { copy(task = task.apply { isFavorite = isFavorite.not() }) }

    fun loadTask(taskId: Long?) {
        action {
            val task = taskId?.let { taskDS.getTask(it) } ?: Task()
            state { copy(id = task.id, task = task) }
        }
    }

    fun editTask(state: EditTaskState) {
        action {
            if (state.id == null) {
                taskDS.insertTask(state.task)
            } else {
                taskDS.updateTask(state.task)
            }
        }
        clearState()
    }
}