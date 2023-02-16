package com.android.todozen.tasklist

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.ListDataSource
import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.edittasklist.EditTaskListListener
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.runBlocking

class TaskListViewModel(
    private val taskDS: TaskDataSource,
    private val listDS: ListDataSource,
    val eventsDispatcher: EventsDispatcher<EditTaskListListener>
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    private var job: Job? = null

    init {
        action {
            val list = listDS.getMyDayList()
            loadListTasks(list)
        }
    }

    fun loadListTasks(list: TaskList) {
        job?.cancel()
        when (list) {
            is EditableList -> loadEditableListTasks(list)
            is InternalList -> loadInternalListTasks(list)
        }
    }

    private fun loadEditableListTasks(list: EditableList) {
        job = action {
            taskDS.getTasks(list.id).cancellable()
                .collect { state { copy(list = list, tasks = it) } }
        }
    }

    private fun loadInternalListTasks(list: InternalList) {
        job = when (list.type) {
            ALL -> action {
                taskDS.getAllTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            MY_DAY -> action {
                taskDS.getMyDayTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            TOMORROW -> action {
                taskDS.getAllTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            NEXT_WEEK -> action {
                taskDS.getAllTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            INCOMING -> action {
                taskDS.getAllTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            FAVORITE -> action {
                taskDS.getFavoriteTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            DONE -> action {
                taskDS.getAllTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
            DELETED -> action {
                taskDS.getDeletedTasks().cancellable()
                    .collect { state { copy(list = list, tasks = it) } }
            }
        }
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

    fun showSorts() {
        eventsDispatcher.dispatchEvent { showSorts(Sort.values().toList()) }
    }

    fun updateSort(sort: Sort) {
        action {
            val list = state.value.list.apply { this?.sort = sort }
            when (list) {
                is EditableList -> listDS.updateEditableList(list)
                is InternalList -> listDS.updateInternalList(list)
            }
            state { copy(list = list) }
        }
    }
}