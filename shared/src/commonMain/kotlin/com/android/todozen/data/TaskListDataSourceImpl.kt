package com.android.todozen.data

import com.android.todozen.TaskDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.android.todozen.domain.TaskList
import com.android.todozen.utils.map
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskListDataSourceImpl(db: TaskDatabase) : TaskListDataSource {

    private val queries = db.taskQueries

    override suspend fun editTaskList(taskList: TaskList) {
        queries.editTaskList(
            id = taskList.id,
            title = taskList.title
        )
    }

    override suspend fun deleteTaskList(id: Long) = queries.deleteTaskList(id)

    override suspend fun getTaskList(id: Long): TaskList {
        return queries.getTaskList(id).executeAsOne().map()
    }

    override fun getAllTaskLists(): Flow<List<TaskList>> {
        return queries.getAllTaskLists().asFlow().mapToList().map { it.map { it.map() } }
    }
}