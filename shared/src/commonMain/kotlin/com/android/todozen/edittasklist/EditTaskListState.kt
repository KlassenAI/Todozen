package com.android.todozen.edittasklist

import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseState

data class EditTaskListState(
    val id: Long? = null,
    val title: String = ""
): BaseState {

    fun getTaskList() = TaskList(
        id = id,
        title = title,
    )

    companion object {
        fun getFromTaskList(taskList: TaskList?) = EditTaskListState(
            id = taskList?.id,
            title = taskList?.title.orEmpty()
        )
    }
}
