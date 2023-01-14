package com.android.todozen.features.edittasklist

import com.android.todozen.domain.TaskList
import com.android.todozen.utils.BaseState

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
