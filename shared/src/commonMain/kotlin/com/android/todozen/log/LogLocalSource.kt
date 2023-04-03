package com.android.todozen.log

import database.TaskLogEntity

interface LogLocalSource {
    suspend fun logTask(taskLogData: TaskLogData)
    suspend fun getTaskLogsByCategory(categoryId: Long?): List<TaskLogEntity>
    suspend fun getAllTaskLogs(): List<TaskLogEntity>
}