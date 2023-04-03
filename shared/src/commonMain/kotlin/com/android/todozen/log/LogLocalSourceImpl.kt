package com.android.todozen.log

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.getString
import com.android.todozen.core.domain.toLocalDateTime
import com.android.todozen.core.domain.toLong
import com.android.todozen.core.expect.getString
import database.TaskLogEntity

class LogLocalSourceImpl(db: TaskDatabase): LogLocalSource {

    private val queries = db.taskQueries

    override suspend fun logTask(taskLogData: TaskLogData) {
        queries.makeTaskLog(
            taskLogType = taskLogData.type.name,
            dateTime = taskLogData.date.toLong(),
            taskId = taskLogData.taskId,
            taskTitle = taskLogData.taskTitle,
            taskCategoryId = taskLogData.taskCategoryId
        )
    }

    override suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLogEntity> {
        return queries.getTaskLogsByCategory(categoryId).executeAsList()
    }

    override suspend fun getAllTaskLogs(): List<TaskLogEntity> {
        return queries.getAllTaskLogs().executeAsList()
    }
}
