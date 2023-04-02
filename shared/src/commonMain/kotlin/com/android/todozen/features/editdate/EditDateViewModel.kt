package com.android.todozen.features.editdate

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.domain.RepeatType
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditDateViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<EditDateState>() {

    override fun initialState() = EditDateState()

    fun updateDate(date: LocalDate?) = state { copy(date = date) }

    fun updateTime(time: LocalTime?) = state { copy(time = time) }

    fun updateRepeat(repeat: RepeatType) = state { copy(repeat = repeat) }
}