package com.android.todozen.features.menu

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.presentation.BaseViewModel.BaseViewModelState

data class MenuState(
    val internalLists: List<InternalList> = emptyList(),
    val editableLists: List<EditableList> = emptyList()
): BaseViewModelState
