package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.android.todozen.core.domain.Task
import com.android.todozen.core.domain.map
import com.android.todozen.core.domain.toLong
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
            listId = task.listId
        )
    }

    override suspend fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    override suspend fun getTask(id: Long, listId: Long?): Task {
        return if (listId == null) queries.getTask(id).executeAsOne().map()
        else queries.getTaskWithListId(id).executeAsOne().map()
    }

    override fun getTasks(listId: Long?): Flow<List<Task>> {
        return if (listId == null) queries.getIncomingTasks().asFlow().mapToList().map { it.map { it.map() } }
        else queries.getTasks(listId).asFlow().mapToList().map { it.map { it.map() } }
    }
}