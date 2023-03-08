package com.android.todozen.editdate

import com.android.todozen.core.domain.RepeatType
import com.android.todozen.core.presentation.BaseState
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

data class EditDateState(
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val repeat: RepeatType = RepeatType.DEFAULT
) : BaseState
