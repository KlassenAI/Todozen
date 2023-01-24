package com.android.todozen.core.domain

import database.*
import kotlinx.datetime.*

fun TaskEntity.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = listId,
    listTitle = "",
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetTask.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetTasksForToday.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetAllTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetFavoriteTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
)

fun GetDeletedTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    listId = id_,
    listTitle = title_.orEmpty(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    updated = updated.toLocalDateTime()
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
