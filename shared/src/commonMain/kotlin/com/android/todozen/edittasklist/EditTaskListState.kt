package com.android.todozen.edittasklist

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.presentation.BaseState

data class EditTaskListState(
    val id: Long? = null,
    val taskList: EditableList = EditableList(),
): BaseState
