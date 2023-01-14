package com.android.todozen.domain

import com.android.todozen.utils.DateTimeUtil
import com.android.todozen.utils.ListItem
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Task(
    val id: Long? = null,
    val title: String = "",
    var done: Boolean = false,
    var date: LocalDate? = null,
    var time: LocalTime? = null,
    val created: LocalDateTime = DateTimeUtil.now(),
    val taskList: Long? = null,
) : ListItem {
    override fun getUuid() = id
}
