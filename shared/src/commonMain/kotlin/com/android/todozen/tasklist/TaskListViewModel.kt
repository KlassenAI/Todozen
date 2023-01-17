package com.android.todozen.tasklist

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    init {
        loadTasks(null)
    }

    fun loadTasks(id: Long?) {
        viewModelScope.launch {
            taskDS.getTasks(id).collect {
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