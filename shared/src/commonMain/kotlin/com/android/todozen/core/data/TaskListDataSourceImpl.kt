package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.domain.map
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskListDataSourceImpl(db: TaskDatabase) : TaskListDataSource {

    private val queries = db.taskQueries

    override suspend fun insertTaskList(taskList: TaskList) {
        queries.insertTaskList(
            id = null,
            title = taskList.title,
            isFavorite = taskList.isFavorite,
            color = taskList.color?.toLong(),
            position = taskList.position
        )
    }

    override suspend fun updateTaskList(taskList: TaskList) {
        updateQueryTaskList(taskList)
    }

    override suspend fun deleteTaskList(id: Long) {
        queries.deleteTaskList(id)
    }

    override suspend fun getTaskList(id: Long): TaskList {
        return queries.getTaskList(id).executeAsOne().map()
    }

    override fun getFlowTaskLists(): Flow<List<TaskList>> {
        return queries.getAllTaskLists().asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getTaskListsCount(): Long = queries.getTaskListsCount().executeAsOne()

    override suspend fun updateTaskLists(taskList: TaskList, taskList2: TaskList) {
        return queries.transaction {
            updateQueryTaskList(taskList)
            updateQueryTaskList(taskList2)
        }
    }

    private fun updateQueryTaskList(taskList: TaskList) {
        queries.updateTaskList(
            id = taskList.id,
            title = taskList.title,
            isFavorite = taskList.isFavorite,
            color = taskList.color?.toLong(),
            position = taskList.position
        )
    }

    override suspend fun getTaskLists(): List<TaskList> {
        return queries.getAllTaskLists().executeAsList().map { it.map() }
    }
}