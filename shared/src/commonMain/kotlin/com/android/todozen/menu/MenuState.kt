package com.android.todozen.menu

import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.presentation.BaseState

data class MenuState(
    val taskLists: List<TaskList> = emptyList()
): BaseState
