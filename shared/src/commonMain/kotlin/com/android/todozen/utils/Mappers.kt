package com.android.todozen.utils

import com.android.todozen.domain.Task
import com.android.todozen.domain.TaskList
import database.TaskEntity
import database.TaskListEntity
import kotlinx.datetime.*

fun TaskEntity.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
)

fun TaskListEntity.map() = TaskList(
    id = id,
    title = title,
)

fun LocalDateTime.toLong(): Long = this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun Long.toLocalDateTime(): LocalDateTime = Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.toLong(): Long = this.toEpochDays().toLong()

fun Long.toLocalDate(): LocalDate = LocalDate.fromEpochDays(this.toInt())

fun LocalTime.toLong(): Long = toNanosecondOfDay()

fun Long.toLocalTime(): LocalTime = LocalTime.fromNanosecondOfDay(this)
