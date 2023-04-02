package com.android.todozen.features.tasklist

import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.UseCaseResult.Success
import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.core.usecases.*
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable

class TaskListViewModel(
    private val getMyDayListUseCase: GetMyDayListUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val getSortsUseCase: GetSortsUseCase,
    private val getTaskListLevelUseCase: GetTaskListLevelUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val completeTaskUseCase: CompleteTaskUseCase,
    private val createCompleteTaskActionUseCase: CreateCompleteTaskActionUseCase,
    private val createNextRecurringTaskUseCase: CreateNextRecurringTaskUseCase,
    private val changeListSortUseCase: ChangeListSortUseCase,
    private val getActionsUseCase: GetActionsUseCase,
    val eventsDispatcher: EventsDispatcher<EventsListener>
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    private var job: Job? = null

    init {
        action {
            val myDayList = getMyDayListUseCase.execute()
            getListTasks(myDayList)
        }
    }

    suspend fun getActions() = getActionsUseCase.execute()

    fun getListTasks(list: TaskList) {
        job?.cancel()
        job = action {
            getTasksUseCase.execute(list).cancellable().collect {
                state { copy(list = list, tasks = it) }
            }
        }
        getListPoints(list)
    }

    fun checkTask(task: Task) {
        action {
            if (createNextRecurringTaskUseCase.execute(task) is Success) {
                eventsDispatcher.dispatchEvent { showMessageNextRecurringTaskCreated() }
            }
            completeTaskUseCase.execute(task)

            val createCompleteTaskActionUseCase = createCompleteTaskActionUseCase.execute(task)
            if (createCompleteTaskActionUseCase is Success) {
                eventsDispatcher.dispatchEvent {
                    showSnackbarActionAdded(createCompleteTaskActionUseCase.data)
                }
            }
            // проверяем достижения
            // todo дополнить проверку для достижений
            getListPoints(it.list!!)
        }
    }

    fun deleteTask(task: Task) {
        action { deleteTaskUseCase.execute(task) }
    }

    fun showSorts() {
        eventsDispatcher.dispatchEvent {
            showSorts(getSortsUseCase.execute())
        }
    }

    fun updateSort(sort: Sort) {
        action {
            changeListSortUseCase.execute(it.list!!, sort)
            state { copy(list = list) }
        }
    }

    private fun getListPoints(list: TaskList) {
        action {
            val taskListLevel = getTaskListLevelUseCase.execute(list)
            state { copy(taskListLevel = taskListLevel) }
        }
    }

    fun showAddTaskDialog() {
        action {
            eventsDispatcher.dispatchEvent { showAddActionDialog(state.value.list!!) }
        }
    }

    fun navigateToActionLog() {
        action {
            val categoryId = if (it.list is EditableList) it.list.id else 0
            eventsDispatcher.dispatchEvent { navigateToActionLog(categoryId) }
        }
    }

    interface EventsListener {
        fun showAddActionDialog(list: TaskList)
        fun showMessageNextRecurringTaskCreated()
        fun showSnackbarActionAdded(action: Action)
        fun showSorts(sorts: List<Sort>)

        fun navigateToActionLog(categoryId: Long?)
    }
}