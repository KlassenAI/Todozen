package com.android.todozen.data

import com.android.todozen.TaskDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.android.todozen.domain.Task
import com.android.todozen.utils.map
import com.android.todozen.utils.toLong
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TaskDataSourceImpl(db: TaskDatabase) : TaskDataSource {

    private val queries = db.taskQueries

    override suspend fun editTask(task: Task) {
        queries.editTask(
            id = task.id,
            title = task.title,
            done = task.done,
            date = task.date?.toLong(),
            time = task.time?.toLong(),
            created = task.created.toLong(),
            taskList = task.taskList
        )
    }

    override suspend fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    override suspend fun getTask(id: Long): Task {
        return queries.getTask(id).executeAsOne().map()
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return queries.getAllTasks().asFlow().mapToList().map { it.map { it.map() } }
    }
}