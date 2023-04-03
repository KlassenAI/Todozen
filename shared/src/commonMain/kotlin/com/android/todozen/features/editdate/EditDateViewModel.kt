package com.android.todozen.features.editdate

import com.android.todozen.task.TaskLocalSource
import com.android.todozen.core.domain.RepeatType
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditDateViewModel(
    private val taskDS: TaskLocalSource
) : BaseViewModel<EditDateState>() {

    override fun initialState() = EditDateState()

    fun updateDate(date: LocalDate?) = state { copy(date = date) }

    fun updateTime(time: LocalTime?) = state { copy(time = time) }

    fun updateRepeat(repeat: RepeatType) = state { copy(repeat = repeat) }
}