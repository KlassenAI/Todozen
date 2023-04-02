package com.android.todozen.features.actionlog

class ActionLogRepository(private val localSource: ActionLogLocalSource) {

    suspend fun logTask(taskLog: TaskLog) = localSource.logTask(taskLog)

    suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLog> {
        return localSource.getTaskLogsByCategory(categoryId)
    }

    suspend fun getAllTaskLogs() = localSource.getAllTaskLogs()
}