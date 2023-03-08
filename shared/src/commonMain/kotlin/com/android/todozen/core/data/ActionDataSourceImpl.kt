package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.Action
import com.android.todozen.core.domain.ActionType
import com.android.todozen.core.domain.map

class ActionDataSourceImpl(db: TaskDatabase): ActionDataSource {

    private val queries = db.taskQueries

    override suspend fun getAllPoints(): Long {
        return queries.getAllPoints().executeAsOne()
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
}