package com.android.todozen.tasklist

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.domain.DateTimeUtil
import com.android.todozen.core.domain.Task
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil

class TaskListViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    private var job: Job? = null

    init {
        loadAllTasks()
    }

    fun loadAllTasks() {
        job?.cancel()
        job = action { taskDS.getAllTasks().cancellable().collect { updateTasks(it) } }
    }

    fun loadTasksForToday() {
        job?.cancel()
        job = action { taskDS.getTasksForToday().cancellable().collect { updateTasks(it) } }
    }

    fun loadTasks(id: Long?) {
        job?.cancel()
        job = action { taskDS.getTasks(id).cancellable().collect { updateTasks(it) } }
    }

    fun loadFavoriteTasks() {
        job?.cancel()
        job = action { taskDS.getFavoriteTasks().cancellable().collect { updateTasks(it) } }
    }

    fun loadDeletedTasks() {
        job?.cancel()
        job = action { taskDS.getDeletedTasks().cancellable().collect { updateTasks(it) } }
    }

    fun checkTask(task: Task) {
        action { taskDS.updateTask(task.apply { isDone = isDone.not() }) }
    }

    fun deleteTask(task: Task) {
        action {
            if (task.isDeleted) {
                taskDS.deleteTask(task.id!!)
            } else {
                taskDS.updateTask(task.apply { isDeleted = isDeleted.not() })
            }
        }
    }

    private fun updateTasks(tasks: List<Task>) = state { copy(tasks = tasks) }
}