package com.android.todozen.core.domain

import com.android.todozen.SharedRes
import com.android.todozen.action.TaskActionType
import com.android.todozen.core.expect.getString
import com.android.todozen.task.Task
import database.*
import kotlinx.datetime.*
import kotlinx.datetime.DayOfWeek.*
import kotlinx.datetime.Month.*

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
    type = TaskActionType.valueOf(actionType),
    task = Task(
        id = id_!!,
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
    type = TaskActionType.valueOf(actionType),
    task = Task(
        id = id_!!,
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

fun LocalTime.getString(): String {
    return "${hour.getTimeStr()}:${minute.getTimeStr()}:${second.getTimeStr()}";
}

private fun Int.getTimeStr() = toString().padStart(2, '0')

fun Month.getString(): String = when(this) {
    JANUARY -> getString(SharedRes.strings.month_short_january)
    FEBRUARY -> getString(SharedRes.strings.month_short_february)
    MARCH -> getString(SharedRes.strings.month_short_march)
    APRIL -> getString(SharedRes.strings.month_short_april)
    MAY -> getString(SharedRes.strings.month_short_may)
    JUNE -> getString(SharedRes.strings.month_short_january)
    JULY -> getString(SharedRes.strings.month_short_january)
    AUGUST -> getString(SharedRes.strings.month_short_january)
    SEPTEMBER -> getString(SharedRes.strings.month_short_january)
    OCTOBER -> getString(SharedRes.strings.month_short_january)
    NOVEMBER -> getString(SharedRes.strings.month_short_january)
    DECEMBER -> getString(SharedRes.strings.month_short_january)
    else -> getString(SharedRes.strings.month_short_january)
}

fun DayOfWeek.getString(): String = when(this) {
    MONDAY -> getString(SharedRes.strings.weekday_monday)
    TUESDAY -> getString(SharedRes.strings.weekday_tuesday)
    WEDNESDAY -> getString(SharedRes.strings.weekday_wednesday)
    THURSDAY -> getString(SharedRes.strings.weekday_thursday)
    FRIDAY -> getString(SharedRes.strings.weekday_friday)
    SATURDAY -> getString(SharedRes.strings.weekday_saturday)
    SUNDAY -> getString(SharedRes.strings.weekday_sunday)
    else -> getString(SharedRes.strings.weekday_monday)
}

// The day of the week comes from the days of the era.
// Week Zero is the week of January 1, 1970.
fun LocalDate.getWeekNumberAndDaysRemaining(): Pair<Int, Int> {
    return Pair((toEpochDays() + 3) / 7, (toEpochDays() + 3) % 7)
}

fun getLocalDateFromWeekNumberAndDaysRemaining(weekNumber: Int, daysRemaining: Int): LocalDate {
    return LocalDate.fromEpochDays(weekNumber * 7 + daysRemaining - 3)
}
