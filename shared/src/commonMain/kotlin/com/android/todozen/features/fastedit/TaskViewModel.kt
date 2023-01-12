package com.android.todozen.features.fastedit

import com.android.todozen.domain.Task
import com.android.todozen.domain.TaskDataSource
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class TaskViewModel(
    private val taskDS: TaskDataSource
) : ViewModel() {

    private val _state = MutableLiveData(TaskState())
    val state: LiveData<TaskState> get() = _state

    private fun updateState(update: TaskState.() -> TaskState) {
        _state.value = update(_state.value)
    }

    fun loadTask(taskId: Long?) {
        viewModelScope.launch {
            val task = taskId?.let { taskDS.getTask(it) } ?: Task()
            updateState { copy(id = task.id, title = task.title) }
        }
    }

    fun editTask() {
        val state = _state.value
        val task = Task(id = state.id, title = state.title)
        viewModelScope.launch {
            if (task.id == null) {
                taskDS.addTask(task)
            } else {
                taskDS.updateTask(task)
            }
        }
    }

    fun updateTitle(title: String) {
        updateState { copy(title = title) }
    }
}