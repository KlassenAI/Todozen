package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.DateTimeUtil
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.android.todozen.core.domain.Task
import com.android.todozen.core.domain.map
import com.android.todozen.core.domain.toLong
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

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
            listId = task.listId,
            inMyDay = task.inMyDay
        )
    }

    override suspend fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    override suspend fun getTask(id: Long): Task {
        return queries.getTask(id).executeAsOne().map()
    }

    override fun getTasks(listId: Long?): Flow<List<Task>> {
        return queries.getTasks(listId).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return queries.getAllTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getTasksForToday(): Flow<List<Task>> {
        return queries.getTasksForToday(true, DateTimeUtil.today().toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }
}