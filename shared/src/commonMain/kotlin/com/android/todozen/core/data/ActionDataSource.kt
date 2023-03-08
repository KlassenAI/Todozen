package com.android.todozen.core.data

import com.android.todozen.core.domain.Action

interface ActionDataSource {
    suspend fun getAllPoints(): Long
    suspend fun getListPoints(listId: Long): Long
    suspend fun getAllActions(): List<Action>
    suspend fun getTaskActions(taskId: Long): List<Action>
}