package com.android.todozen.tasklist

import com.android.todozen.core.data.ActionDataSource
import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.ListDataSource
import com.android.todozen.core.data.TaskRepository
import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.edittasklist.EditTaskListListener
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

class TaskListViewModel(
    private val taskDS: TaskDataSource,
    private val listDS: ListDataSource,
    private val actionDS: ActionDataSource,
    private val taskRepository: TaskRepository,
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
        val taskFlow = when(list.type) {
            ALL -> taskDS.getAllTasks()
            MY_DAY -> taskDS.getMyDayTasks()
            TOMORROW -> taskDS.getAllTasks()
            NEXT_WEEK -> taskDS.getAllTasks()
            INCOMING -> taskDS.getAllTasks()
            FAVORITE -> taskDS.getFavoriteTasks()
            DONE -> taskDS.getAllTasks()
            DELETED -> taskDS.getDeletedTasks()
        }
        job = action {
            taskFlow.cancellable().collect {
                state { copy(list = list, tasks = it) }
            }
        }
    }

    fun checkTask(task: Task) {
        action {
            taskRepository.checkTask(task)
            // проверяем достижения
            // todo дополнить проверку для достижений
            // обновляем очки
            // todo дополнить функцию обновления
        }
    }

    fun deleteTask(task: Task) {
        action {
            if (task.isDeleted) {
                taskDS.deleteTask(task.id)
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

    suspend fun getAllPoints() = actionDS.getAllPoints()

    suspend fun getActions() = actionDS.getAllActions()
}