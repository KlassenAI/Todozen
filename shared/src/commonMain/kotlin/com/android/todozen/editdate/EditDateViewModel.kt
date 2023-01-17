package com.android.todozen.editdate

import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.presentation.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditDateViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<EditDateState>() {

    override fun initialState() = EditDateState()

    fun updateDate(date: LocalDate?) = updateState { copy(date = date) }

    fun updateTime(time: LocalTime?) = updateState { copy(time = time) }
}