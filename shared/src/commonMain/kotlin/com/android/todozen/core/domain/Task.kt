package com.android.todozen.core.domain

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

data class Task(
    val id: Long? = null,
    val created: LocalDateTime = DateTimeUtil.now(),
    var title: String = "",
    var isDone: Boolean = false,
    var date: LocalDate? = null,
    var time: LocalTime? = null,
    var listId: Long? = null,
    var listTitle: String = "",
    var listColor: Int? = null,
    var isInMyDay: Boolean = false,
    var isDeleted: Boolean = false,
    var isFavorite: Boolean = false,
    var updated: LocalDateTime = DateTimeUtil.now()
) : ListItem {
    override fun getUuid() = id
}
