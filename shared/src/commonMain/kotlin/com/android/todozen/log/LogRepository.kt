package com.android.todozen.log

import database.TaskLogEntity

class LogRepository(private val localSource: LogLocalSource) {

    suspend fun logTask(taskLogData: TaskLogData) = localSource.logTask(taskLogData)

    suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLogEntity> {
        return localSource.getTaskLogsByCategory(categoryId)
    }

    suspend fun getAllTaskLogs() = localSource.getAllTaskLogs()
}