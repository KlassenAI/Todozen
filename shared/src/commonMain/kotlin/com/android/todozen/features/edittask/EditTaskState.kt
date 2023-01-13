package com.android.todozen.features.edittask

import com.android.todozen.utils.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditTaskState(
    val id: Long? = null,
    val title: String = "",
    val date: LocalDate? = null,
    val time: LocalTime? = null
): BaseState