package com.android.todozen.core.domain

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
    val listId: Long? = null,
    val listTitle: String = ""
) : ListItem {
    override fun getUuid() = id
}
