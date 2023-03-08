package com.android.todozen.core.domain

import database.*
import kotlinx.coroutines.flow.Flow
import kotlinx.datetime.*

fun GetTask.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    repeat = repeat.toRepeatType(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun GetTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    repeat = repeat.toRepeatType(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun GetMyDayTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    repeat = repeat.toRepeatType(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun GetTomorrowTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    repeat = repeat.toRepeatType(),
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun GetNextWeekTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    repeat = repeat.toRepeatType(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
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
    repeat = repeat.toRepeatType(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun GetDoneTasks.map() = Task(
    id = id,
    title = title,
    isDone = isDone,
    isInMyDay = isInMyDay,
    isDeleted = isDeleted,
    isFavorite = isFavorite,
    date = date?.toLocalDate(),
    time = time?.toLocalTime(),
    repeat = repeat.toRepeatType(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
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
    repeat = repeat.toRepeatType(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
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
    repeat = repeat.toRepeatType(),
    created = created.toLocalDateTime(),
    updated = updated.toLocalDateTime(),
    list = listId?.let { EditableList(id_ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
    priority = Priority(name.toPriorityType(), color_!!.toInt())
)

fun EditableListEntity.map() = EditableList(
    id = id,
    title = title,
    isFavorite = isFavorite,
    color = color?.toInt(),
    position = position,
    sort = Sort.getById(sortId)
)

fun InternalListEntity.map() = InternalList(
    type = InternalListType.getById(id),
    position = position.toInt(),
    sort = Sort.getById(sortId),
    taskCount = 0
)

fun PriorityEntity.map() = Priority(
    type = name.toPriorityType(),
    color = color.toInt(),
)

fun GetAllActions.map() = Action(
    id = id,
    points = points,
    type = ActionType.valueOf(actionType),
    task = Task(
        id = id_,
        title = title.orEmpty(),
        isDone = isDone.orDefault(),
        isInMyDay = isInMyDay.orDefault(),
        isDeleted = isDeleted.orDefault(),
        isFavorite = isFavorite.orDefault(),
        date = date?.toLocalDate(),
        time = time?.toLocalTime(),
        repeat = repeat.toRepeatType(),
        created = created!!.toLocalDateTime(),
        updated = updated!!.toLocalDateTime(),
        list = listId?.let { EditableList(id__ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
        priority = Priority(name.toPriorityType(), color_!!.toInt())
    )
)

fun GetTaskActions.map() = Action(
    id = id,
    points = points,
    type = ActionType.valueOf(actionType),
    task = Task(
        id = id__,
        title = title.orEmpty(),
        isDone = isDone.orDefault(),
        isInMyDay = isInMyDay.orDefault(),
        isDeleted = isDeleted.orDefault(),
        isFavorite = isFavorite.orDefault(),
        date = date?.toLocalDate(),
        time = time?.toLocalTime(),
        repeat = repeat.toRepeatType(),
        created = created!!.toLocalDateTime(),
        updated = updated!!.toLocalDateTime(),
        list = listId?.let { EditableList(id___ ?: 0, title_.orEmpty(), isFavorite_ ?: false, color?.toInt(), position ?: 0) },
        priority = Priority(name.toPriorityType(), color_!!.toInt())
    )
)

fun String?.toPriorityType(): PriorityType {
    return this?.let { PriorityType.valueOf(this.uppercase()) } ?: PriorityType.DEFAULT
}

fun String?.toRepeatType(): RepeatType {
    return this?.let { RepeatType.valueOf(this.uppercase()) } ?: RepeatType.DEFAULT
}

fun LocalDateTime.toLong(): Long =
    this.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()

fun Long.toLocalDateTime(): LocalDateTime =
    Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())

fun LocalDate.toLong(): Long = this.toEpochDays().toLong()

fun Long.toLocalDate(): LocalDate = LocalDate.fromEpochDays(this.toInt())

fun LocalTime.toLong(): Long = toNanosecondOfDay()

fun Long.toLocalTime(): LocalTime = LocalTime.fromNanosecondOfDay(this)

inline fun Boolean?.orDefault(): Boolean = this ?: false
