package com.android.todozen.features.actionlog

import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.features.actionlog.ActionLogViewModel.ActionLogState

class ActionLogViewModel(
    private val logsInteractor: LogsInteractor
) : BaseViewModel<ActionLogState>() {

    override fun initialState() = ActionLogState()

    fun init(taskCategoryId: Long?) {
        action {
            state { copy(isLoading = true, taskCategoryId = taskCategoryId) }
            val taskLogs = logsInteractor.getTaskLogs(taskCategoryId)
            state { copy(isLoading = false, taskLogs = taskLogs) }
        }
    }

    fun refreshData() {
        action {
            val taskLogs = logsInteractor.getTaskLogs(it.taskCategoryId)
            state { copy(isLoading = false, taskLogs = taskLogs) }
        }
    }

    data class ActionLogState(
        val isLoading: Boolean = true,
        val taskCategoryId: Long? = 0,
        val taskLogs: List<TaskLog> = emptyList(),
    ): BaseViewModelState
}