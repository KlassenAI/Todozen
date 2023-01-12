package com.android.todozen.domain

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Long? = null,
    val title: String = "",
    var done: Boolean = false,
    val created: LocalDateTime = DateTimeUtil.now(),
): ListItem {
    override fun getUuid() = id
}
