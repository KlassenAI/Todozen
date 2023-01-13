package com.android.todozen.features.edittask

import com.android.todozen.domain.Task
import com.android.todozen.data.TaskDataSource
import com.android.todozen.utils.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditTaskViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<EditTaskState>() {

    override fun initialState() = EditTaskState()

    fun updateTitle(title: String) = updateState { copy(title = title) }
    fun updateDateTime(date: LocalDate?, time: LocalTime?) {
        updateState { copy(date = date, time = time) }
    }

    fun loadTask(taskId: Long?) {
        viewModelScope.launch {
            val task = taskId?.let { taskDS.getTask(it) } ?: Task()
            updateState { copy(id = task.id, title = task.title, date = task.date, time = time) }
        }
    }

    fun editTask() {
        val state = _state.value
        val task = Task(id = state.id, title = state.title, date = state.date, time = state.time)
        viewModelScope.launch {
            if (task.id == null) {
                taskDS.addTask(task)
            } else {
                taskDS.updateTask(task)
            }
        }
    }

}