package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

class TaskDataSourceImpl(db: TaskDatabase) : TaskDataSource {

    private val queries = db.taskQueries

    override suspend fun insertTask(task: Task) {
        queries.insertTask(
            id = null,
            created = DateTimeUtil.now().toLong(),
            updated = DateTimeUtil.now().toLong(),
            title = task.title,
            isDone = task.isDone,
            date = task.date?.toLong(),
            time = task.time?.toLong(),
            listId = task.list.id,
            isInMyDay = task.isInMyDay,
            isDeleted = task.isDeleted,
            isFavorite = task.isFavorite,
            priorityId = task.priority.id
        )
    }

    override suspend fun updateTask(task: Task) {
        queries.updateTask(
            id = task.id,
            created = task.created.toLong(),
            updated = DateTimeUtil.now().toLong(),
            title = task.title,
            isDone = task.isDone,
            date = task.date?.toLong(),
            time = task.time?.toLong(),
            listId = task.list.id,
            isInMyDay = task.isInMyDay,
            isDeleted = task.isDeleted,
            isFavorite = task.isFavorite,
            priorityId = task.priority.id
        )
    }

    override suspend fun deleteTask(id: Long) {
        queries.deleteTask(id)
    }

    override suspend fun getTask(id: Long): Task {
        return queries.getTask(id).executeAsOne().map()
    }

    override suspend fun getPriorities(): List<Priority> {
        return queries.getPriorities().executeAsList().map { it.map() }
    }

    override fun getTasks(listId: Long?): Flow<List<Task>> {
        return queries.getTasks(listId, false).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return queries.getAllTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getTasksForToday(): Flow<List<Task>> {
        return queries.getTasksForToday(true, DateTimeUtil.today().toLong(), false).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getFavoriteTasks(): Flow<List<Task>> {
        return queries.getFavoriteTasks(isFavorite = true, isDeleted = false).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getDeletedTasks(): Flow<List<Task>> {
        return queries.getDeletedTasks(true).asFlow().mapToList().map { it.map { it.map() } }
    }
}