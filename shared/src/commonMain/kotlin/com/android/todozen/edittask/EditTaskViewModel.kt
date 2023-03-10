package com.android.todozen.edittask

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.domain.*
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

    fun updateDateTime(date: LocalDate?, time: LocalTime?, repeat: RepeatType) {
        state { copy(task = task.apply { this.date = date; this.time = time; this.repeat = repeat }) }
    }

    fun updateTaskList(taskList: EditableList) {
        state { copy(task = task.apply { this.list = taskList }, isListPicked = true) }
    }

    fun updateInMyDay() = state { copy(task = task.apply { isInMyDay = isInMyDay.not() }) }

    fun updateFavorite() = state { copy(task = task.apply { isFavorite = isFavorite.not() }) }

    fun updatePriority(priority: Priority) {
        state { copy(task = task.apply { this.priority = priority }) }
    }

    fun updateList(list: TaskList) = state { copy(taskList = list) }

    fun loadTask(taskId: Long?) {
        action {
            val task = taskId?.let { taskDS.getTask(it) } ?: Task()
            state { copy(id = if (task.id == 0L) null else task.id, task = task, isListPicked = taskId != null) }
        }
    }

    fun editTask() {
        action {
            val task = it.task.apply { list = it.list }
            if (it.id == null) {
                taskDS.insertTask(task)
            } else {
                taskDS.updateTask(task)
            }
        }
        clearState()
    }

    fun showPriorities() {
        action {
            val priorities = taskDS.getPriorities()
            eventsDispatcher.dispatchEvent { showPriorities(priorities) }
        }
    }
}