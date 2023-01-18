package com.android.todozen.core.domain

import database.*
import kotlinx.datetime.*

fun TaskEntity.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = null,
    listTitle = "",
    inMyDay = inMyDay
)

fun GetTask.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    inMyDay = inMyDay
)

fun GetTasks.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    inMyDay = inMyDay
)

fun GetTasksForToday.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    inMyDay = inMyDay
)

fun GetAllTasks.map() = Task(
    id = id,
    title = title,
    done = done,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    inMyDay = inMyDay
)

fun TaskListEntity.map() = TaskList(
    id = id,
    title = title,
)

fun LocalDateTime.toLong(): Long =
    this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.toLong(): Long = this.toEpochDays().toLong()

fun Long.toLocalDate(): LocalDate = LocalDate.fromEpochDays(this.toInt())

fun LocalTime.toLong(): Long = toNanosecondOfDay()

fun Long.toLocalTime(): LocalTime = LocalTime.fromNanosecondOfDay(this)
