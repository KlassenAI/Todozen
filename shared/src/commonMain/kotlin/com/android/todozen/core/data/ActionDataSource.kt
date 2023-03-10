package com.android.todozen.core.data

import com.android.todozen.core.domain.Action
import com.android.todozen.core.domain.ActionType

interface ActionDataSource {

    suspend fun addAction(action: Action)
    suspend fun deleteAction(actionType: ActionType, taskId: Long)

    suspend fun getAllPoints(): Long
    suspend fun getListPoints(listId: Long): Long
    suspend fun getAllActions(): List<Action>
    suspend fun getTaskActions(taskId: Long): List<Action>
}