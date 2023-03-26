package com.android.todozen.core.data

import com.android.todozen.core.domain.Action
import com.android.todozen.core.domain.ActionType
import com.android.todozen.core.domain.EditableList

class ActionRepository(private val actionLocalSource: ActionDataSource) {

    suspend fun addAction(action: Action) = actionLocalSource.addAction(action)

    suspend fun deleteAction(actionType: ActionType, taskId: Long) {
        actionLocalSource.deleteAction(actionType, taskId)
    }

    suspend fun getGeneralPoints() = actionLocalSource.getAllPoints()

    suspend fun getListPoints(listId: Long) = actionLocalSource.getListPoints(listId)

    suspend fun getActions() = actionLocalSource.getAllActions()
}