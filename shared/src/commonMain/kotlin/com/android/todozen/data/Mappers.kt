package com.android.todozen.data

import com.android.todozen.domain.Task
import database.TaskEntity
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun TaskEntity.map(): Task {
    return Task(
        id = id,
        title = title,
        done = done,
        created = Instant.fromEpochMilliseconds(created).toLocalDateTime(TimeZone.currentSystemDefault())
    )
}