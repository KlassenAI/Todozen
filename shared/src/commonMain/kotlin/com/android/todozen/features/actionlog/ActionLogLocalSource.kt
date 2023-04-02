package com.android.todozen.features.actionlog

interface ActionLogLocalSource {
    suspend fun logTask(taskLog: TaskLog)
    suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLog>
    suspend fun getAllTaskLogs(): List<TaskLog>
}