package com.android.todozen.features.editdate

import com.android.todozen.data.TaskDataSource
import com.android.todozen.utils.BaseViewModel
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime

class EditDateViewModel(
    private val taskDS: TaskDataSource
) : BaseViewModel<EditDateState>() {

    override fun initialState() = EditDateState()

    fun updateDate(date: LocalDate?) = updateState { copy(date = date) }

    fun updateTime(time: LocalTime?) = updateState { copy(time = time) }
}