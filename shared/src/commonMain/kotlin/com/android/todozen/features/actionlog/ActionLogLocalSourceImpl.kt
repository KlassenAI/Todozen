package com.android.todozen.features.actionlog

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.toLong

class ActionLogLocalSourceImpl(db: TaskDatabase): ActionLogLocalSource {

    private val queries = db.taskQueries

    override suspend fun logTask(taskLog: TaskLog) {
        queries.makeTaskLog(
            taskLogType = taskLog.type.name,
            date = taskLog.date.toLong(),
            taskId = taskLog.taskId,
            taskTitle = taskLog.taskTitle,
            taskCategoryId = taskLog.taskCategoryId
        )
    }

    override suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLog> {
        return queries.getTaskLogsByCategory(categoryId).executeAsList().map { it.map() }
    }

    override suspend fun getAllTaskLogs(): List<TaskLog> {
        return queries.getAllTaskLogs().executeAsList().map { it.map() }
    }
}
