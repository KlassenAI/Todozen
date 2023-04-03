package com.android.todozen.action

class TaskActionRepository(private val taskActionLocalSource: TaskActionLocalSource) {

    suspend fun addTaskAction(data: TaskActionData) = taskActionLocalSource.addTaskAction(data)

    suspend fun deleteAction(type: TaskActionType, taskId: Long) {
        taskActionLocalSource.deleteTaskAction(type, taskId)
    }

    suspend fun getGeneralPoints() = taskActionLocalSource.getAllPoints()
    suspend fun getListPoints(listId: Long) = taskActionLocalSource.getListPoints(listId)
    suspend fun getActions() = taskActionLocalSource.getAllActions()
}