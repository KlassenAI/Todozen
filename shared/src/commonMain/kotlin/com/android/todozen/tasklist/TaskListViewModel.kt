package com.android.todozen.tasklist

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable

class TaskListViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    private var allTasksJob: Job? = null
    private var todayTasksJob: Job? = null
    private var tasksJob: Job? = null

    init {
        loadTasks(null)
    }

    fun loadAllTasks() {
        clearJobs()
        allTasksJob = doJob { taskDS.getAllTasks().cancellable().collect { updateTasks(it) } }
    }

    fun loadTasksForToday() {
        clearJobs()
        todayTasksJob = doJob { taskDS.getTasksForToday().cancellable().collect { updateTasks(it) } }
    }

    fun loadTasks(id: Long?) {
        clearJobs()
        tasksJob = doJob { taskDS.getTasks(id).cancellable().collect { updateTasks(it) } }
    }

    fun checkTask(task: Task) {
        doJob { taskDS.editTask(task.apply { done = done.not() }) }
    }

    fun deleteTask(task: Task) {
        doJob { taskDS.deleteTask(task.id!!) }
    }

    private fun updateTasks(tasks: List<Task>) {
        updateState {
            copy(
                tasks = tasks.filter { it.done.not() },
                doneTasks = tasks.filter { it.done }
            )
        }
    }

    private fun clearJobs() {
        allTasksJob?.cancel()
        todayTasksJob?.cancel()
        tasksJob?.cancel()
    }
}