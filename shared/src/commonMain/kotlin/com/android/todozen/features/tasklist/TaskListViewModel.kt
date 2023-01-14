package com.android.todozen.features.tasklist

import com.android.todozen.domain.Task
import com.android.todozen.data.TaskDataSource
import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskDS: TaskDataSource
) : ViewModel() {

    private val _state = MutableLiveData(TaskListState())
    val state: LiveData<TaskListState> get() = _state

    private fun updateState(update: TaskListState.() -> TaskListState) {
        _state.value = update(_state.value)
    }

    init {
        loadTasks()
    }

    fun loadTasks() {
        viewModelScope.launch {
            taskDS.getAllTasks().collect {
                updateState {
                    copy(
                        tasks = it.filter { it.done.not() },
                        doneTasks = it.filter { it.done }
                    )
                }
            }
        }
    }

    fun checkTask(task: Task) {
        viewModelScope.launch { taskDS.editTask(task.apply { done = done.not() }) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch { taskDS.deleteTask(task.id!!) }
    }
}