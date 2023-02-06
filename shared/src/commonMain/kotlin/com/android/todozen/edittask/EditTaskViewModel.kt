package com.android.todozen.edittask

import com.android.todozen.core.domain.Task
import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.TaskListDataSource
import com.android.todozen.core.domain.Priority
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditTaskViewModel(
    private val taskDS: TaskDataSource,
    val eventsDispatcher: EventsDispatcher<EditTaskListener>
) : BaseViewModel<EditTaskState>() {

    override fun initialState() = EditTaskState()

    fun updateTitle(title: String) {
        if (title == state.value.task.title) return
        state { copy(task = task.apply { this.title = title }) }
    }

    fun updateDateTime(date: LocalDate?, time: LocalTime?) {
        state { copy(task = task.apply { this.date = date; this.time = time }) }
    }

    fun updateTaskList(taskList: TaskList) {
        state { copy(task = task.apply { this.list = taskList }) }
    }

    fun updateInMyDay() = state { copy(task = task.apply { isInMyDay = isInMyDay.not() }) }

    fun updateFavorite() = state { copy(task = task.apply { isFavorite = isFavorite.not() }) }

    fun updatePriority(priority: Priority) {
        state { copy(task = task.apply { this.priority = priority }) }
    }

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

    fun showPriorities() {
        action {
            val priorities = taskDS.getPriorities().toMutableList()
            priorities.add(Priority(null, "Нет", -7829368))
            eventsDispatcher.dispatchEvent { showPriorities(priorities) }
        }
    }
}