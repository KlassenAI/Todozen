package com.android.todozen.task

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.*
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.flow.*

class TaskLocalSourceImpl(db: TaskDatabase) : TaskLocalSource {

    private val source = db.taskQueries

    override suspend fun insertTask(task: Task): Long {
        val dateTime = DateTimeUtil.now().toLong()
        return source.transactionWithResult {
            source.insertTask(
                repeat = task.repeat.toString(),
                created = dateTime,
                updated = dateTime,
                title = task.title,
                isDone = task.isDone,
                date = task.date?.toLong(),
                time = task.time?.toLong(),
                listId = task.list?.id,
                isInMyDay = task.isInMyDay,
                isDeleted = task.isDeleted,
                isFavorite = task.isFavorite,
                priorityId = task.priority.type.id
            )
            source.getTaskId(dateTime)
        }.executeAsOne()
    }

    override suspend fun updateTask(task: Task) {
        source.updateTask(
            id = task.id,
            repeat = task.repeat.toString(),
            created = task.created.toLong(),
            updated = DateTimeUtil.now().toLong(),
            title = task.title,
            isDone = task.isDone,
            date = task.date?.toLong(),
            time = task.time?.toLong(),
            listId = task.list?.id,
            isInMyDay = task.isInMyDay,
            isDeleted = task.isDeleted,
            isFavorite = task.isFavorite,
            priorityId = task.priority.type.id
        )
    }

    override suspend fun deleteTask(id: Long) {
        source.deleteTask(id)
    }

    override suspend fun getTask(id: Long): Task {
        return source.getTask(id).executeAsOne().map()
    }

    override suspend fun getPriorities(): List<Priority> {
        return source.getPriorities().executeAsList().map { it.map() }
    }

    override suspend fun getTasks(listId: Long?): Flow<List<Task>> {
        return source.getTasks(listId).asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getMyDayTasks(): Flow<List<Task>> {
        return source.getMyDayTasks(DateTimeUtil.today().toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getTomorrowTasks(): Flow<List<Task>> {
        return source.getTomorrowTasks(DateTimeUtil.tomorrow().toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getNextWeekTasks(): Flow<List<Task>> {
        return source.getNextWeekTasks(DateTimeUtil.tomorrow().toLong(), DateTimeUtil.next(7).toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getIncomingTasks(): Flow<List<Task>> {
        return source.getTasks(null).asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getFavoriteTasks(): Flow<List<Task>> {
        return source.getFavoriteTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getDoneTasks(): Flow<List<Task>> {
        return source.getDoneTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getDeletedTasks(): Flow<List<Task>> {
        return source.getDeletedTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override suspend fun getAllTasks(): Flow<List<Task>> {
        return source.getAllTasks().asFlow().mapToList().map { it.map { it.map() } }
    }
}