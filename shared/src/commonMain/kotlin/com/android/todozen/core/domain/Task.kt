package com.android.todozen.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Task(
    val id: Long? = null,
    val title: String = "",
    var done: Boolean = false,
    val date: LocalDate? = null,
    val time: LocalTime? = null,
    val created: LocalDateTime = DateTimeUtil.now(),
    val listId: Long? = null,
    val listTitle: String = "",
    var inMyDay: Boolean = false
) : ListItem {
    override fun getUuid() = id
}
