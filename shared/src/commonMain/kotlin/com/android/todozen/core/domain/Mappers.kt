package com.android.todozen.core.domain

import database.*
import kotlinx.datetime.*

//fun TaskEntity.map() = Task(
//    id = id,
//    title = title,
//    isDone = isDone,
//    date = date?.toLocalDate(),
//    time = time?.toLocalTime(),
//    created = created.toLocalDateTime(),
//    list = TaskList(listId, "", null)
//    listId = listId,
//    listTitle = "",
//    listColor = null,
//    isInMyDay = isInMyDay,
//    isDeleted = isDeleted,
//    isFavorite = isFavorite,
//    updated = updated.toLocalDateTime(),
//    priorityId = priorityId,
//    priorityType = "",
//    priorityColor = null
//)

fun GetTask.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun GetTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun GetTasksForToday.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun GetAllTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun GetFavoriteTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun GetDeletedTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = TaskList(id_, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0),
    priority = Priority(id__, type, color_?.toInt())
)

fun TaskListEntity.map() = TaskList(
    id = id,
    title = title,
    isFavorite = isFavorite,
    color = color?.toInt(),
    position = position
)

fun PriorityEntity.map() = Priority(
    id = id,
    type = type,
    color = color.toInt(),
)

fun LocalDateTime.toLong(): Long =
    this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.toLong(): Long = this.toEpochDays().toLong()

fun Long.toLocalDate(): LocalDate = LocalDate.fromEpochDays(this.toInt())

fun LocalTime.toLong(): Long = toNanosecondOfDay()

fun Long.toLocalTime(): LocalTime = LocalTime.fromNanosecondOfDay(this)
