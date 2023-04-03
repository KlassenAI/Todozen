package com.android.todozen.action

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.Action
import com.android.todozen.core.domain.map

class TaskActionLocalSourceImpl(db: TaskDatabase): TaskActionLocalSource {

    private val queries = db.taskQueries

    override suspend fun getAllPoints(): Long {
        return queries.getAllPoints().executeAsOne().SUM ?: 0L
    }

    override suspend fun getListPoints(listId: Long): Long {
        return queries.getListPoints(listId).executeAsOne()
    }

    override suspend fun getAllActions(): List<Action> {
        return queries.getAllActions().executeAsList().map { it.map() }
    }

    override suspend fun getTaskActions(taskId: Long): List<Action> {
        return queries.getTaskActions(taskId).executeAsList().map { it.map() }
    }

    override suspend fun addTaskAction(data: TaskActionData) {
        queries.addTaskAction(
            taskActionType = data.type.name,
            taskId = data.taskId,
            taskTitle = data.taskTitle,
            points = data.points
        )
    }

    override suspend fun deleteTaskAction(type: TaskActionType, taskId: Long) {
        queries.deleteTaskAction(
            taskId = taskId,
            taskActionType = type.name
        )
    }
}
