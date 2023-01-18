package com.android.todozen.edittask

import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditTaskState(
    val id: Long? = null,
    val title: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val listId: Long? = null,
    val listTitle: String = "",
    val inMyDay: Boolean = false
): BaseState