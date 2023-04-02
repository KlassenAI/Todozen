package com.android.todozen.features.edittasklist

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.presentation.BaseViewModel.BaseViewModelState

data class EditTaskListState(
    val id: Long? = null,
    val taskList: EditableList = EditableList(),
): BaseViewModelState
