package com.android.todozen.features.tasklist

import com.android.todozen.core.domain.*
import com.android.todozen.core.interactors.ListInteractor
import com.android.todozen.task.TaskInteractor
import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.features.tasklist.TaskListViewModel.TaskListState
import com.android.todozen.task.Task
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.datetime.LocalDate

class TaskListViewModel(
    val eventsDispatcher: EventsDispatcher<EventsListener>,
    private val listInteractor: ListInteractor,
    private val taskInteractor: TaskInteractor,
) : BaseViewModel<TaskListState>() {

    override fun initialState() = TaskListState()

    private var job: Job? = null

    init {
        action {
            val myDayList = listInteractor.getMyDayList()
            getListTasks(myDayList)
        }
    }

    fun getListTasks(list: TaskList) {
        job?.cancel()
        job = action {
            taskInteractor.getTasksByList(list).cancellable().collect {
                state { copy(list = list, tasks = it) }
            }
        }
        getListPoints(list)
    }

    fun checkTask(task: Task) {
        action {
            taskInteractor.checkTask(task)
//             todo дополнить проверку для достижений
            getListPoints(it.list!!)
        }
    }

    fun deleteTask(task: Task) = action { taskInteractor.deleteTask(task) }

    fun showSorts() {
        eventsDispatcher.dispatchEvent {
            val sorts = listInteractor.getListSorts()
            showSorts(sorts)
        }
    }

    fun updateSort(sort: Sort) {
        action {
            listInteractor.changeListSort(it.list!!, sort)
            state { copy(list = list) }
        }
    }

    private fun getListPoints(list: TaskList) {
        action {
//            val taskListLevel = getTaskListLevelUseCase.execute(list)
//            state { copy(taskListLevel = taskListLevel) }
        }
    }

    fun showAddTaskDialog() {
        action {
            eventsDispatcher.dispatchEvent { showAddActionDialog(state.value.list!!) }
        }
    }

    fun navigateToActionLog() {
        action {
            val categoryId = if (it.list is EditableList) it.list.id else 0L
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

    data class TaskListState(
        val isLoading: Boolean = false,
        val list: TaskList? = null,
        val tasks: List<Task> = emptyList(),
        val taskListLevel: TaskListLevel = TaskListLevel(0L)
    ) : BaseViewModelState {

        val lists: List<EditableList?>
            get() = tasks.filter { it.isDone.not() }.map { it.list }.distinct().sortedBy { it?.position }
        val priorities: List<Priority>
            get() = tasks.filter { it.isDone.not() }.map { it.priority }.distinct().sortedBy { it.type.id }

        val currentTasksByTitle: List<Task>
            get() = tasks.filter { it.isDone.not() }.sortedBy { it.title }
        val outdatedTasks: List<Task>
            get() = tasks.filter { it.isDone.not() && it.date?.isOutdated() ?: false }
        val currentTasks: List<Task>
            get() = tasks.filter { it.isDone.not() && it.date?.isOutdated()?.not() ?: true }
        val doneTasks: List<Task> get() = tasks.filter { it.isDone }

        fun getTasksByList(listId: Long?): List<Task> = tasks.filter {
            if (listId == null) {
                it.list == null && it.isDone.not()
            } else {
                it.list != null && it.list!!.id == listId && it.isDone.not()
            }
        }

        fun getTasksByPriority(priority: Priority): List<Task> = tasks.filter {
            it.priority.type.id == priority.type.id && it.isDone.not()
        }

        private fun LocalDate.isOutdated(): Boolean = this < DateTimeUtil.today()
    }
}