package com.android.todozen.features.editdate

import com.android.todozen.utils.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditDateState(
    val date: LocalDate? = null,
    val time: LocalTime? = null
) : BaseState
