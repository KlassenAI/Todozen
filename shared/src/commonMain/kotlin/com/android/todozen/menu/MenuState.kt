package com.android.todozen.menu

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.presentation.BaseState

data class MenuState(
    val internalLists: List<InternalList> = emptyList(),
    val editableLists: List<EditableList> = emptyList()
): BaseState
