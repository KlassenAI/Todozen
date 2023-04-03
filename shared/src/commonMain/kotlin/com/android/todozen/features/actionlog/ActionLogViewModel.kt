package com.android.todozen.features.actionlog

import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.features.actionlog.ActionLogViewModel.ActionLogState
import com.android.todozen.log.LogInteractor
import com.android.todozen.log.TaskLogItem
import com.android.todozen.log.TaskLogListItem

class ActionLogViewModel(
    private val logInteractor: LogInteractor
) : BaseViewModel<ActionLogState>() {

    override fun initialState() = ActionLogState()

    fun init(taskCategoryId: Long?) {
        action {
            state { copy(isLoading = true, taskCategoryId = taskCategoryId) }
            val taskLogs = logInteractor.getTaskLogs(taskCategoryId)
            state { copy(isLoading = false, taskLogs = taskLogs) }
        }
    }

    fun refreshData() {
        action {
            val taskLogs = logInteractor.getTaskLogs(it.taskCategoryId)
            state { copy(isLoading = false, taskLogs = taskLogs) }
        }
    }

    data class ActionLogState(
        val isLoading: Boolean = true,
        val taskCategoryId: Long? = 0,
        val taskLogs: List<TaskLogItem> = emptyList(),
    ): BaseViewModelState
}