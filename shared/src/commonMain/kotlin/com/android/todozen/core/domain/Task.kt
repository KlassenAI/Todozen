package com.android.todozen.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Task(
    var id: Long? = null,
    var title: String = "",
    var isDone: Boolean = false,
    var date: LocalDate? = null,
    var time: LocalTime? = null,
    var repeat: RepeatType = RepeatType.NO,
    var isInMyDay: Boolean = false,
    var isDeleted: Boolean = false,
    var isFavorite: Boolean = false,
    val created: LocalDateTime = DateTimeUtil.now(),
    var updated: LocalDateTime = DateTimeUtil.now(),
    var list: EditableList? = null,
    var priority: Priority = Priority(),
) : ListItem {
    override fun getUuid() = id
}
