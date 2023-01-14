package com.android.todozen.features.menu

import com.android.todozen.domain.TaskList
import com.android.todozen.utils.BaseState

data class MenuState(
    val taskLists: List<TaskList> = emptyList()
): BaseState
