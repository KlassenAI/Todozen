package com.android.todozen.action

import com.android.todozen.core.domain.Action

interface TaskActionLocalSource {

    suspend fun addTaskAction(data: TaskActionData)
    suspend fun deleteTaskAction(type: TaskActionType, taskId: Long)

    suspend fun getAllPoints(): Long
    suspend fun getListPoints(listId: Long): Long
    suspend fun getAllActions(): List<Action>
    suspend fun getTaskActions(taskId: Long): List<Action>
}