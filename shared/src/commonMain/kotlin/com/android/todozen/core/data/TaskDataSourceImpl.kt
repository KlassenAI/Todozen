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
        queries.transaction {
            val dateTime = DateTimeUtil.now().toLong()
            queries.insertTask(
                id = null,
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
            val taskId = queries.getTaskId(dateTime).executeAsOne()
            queries.addAction(
                id = null,
                //todo добавить логику подсчета очков за создание задачи
                points = 100,
                actionType = ActionType.ADD.name,
                taskId = taskId
            )
        }
    }

    override suspend fun updateTask(task: Task) {
        queries.transaction {
            queries.updateTask(
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
            // todo переписать
            if (task.isDone) {
                queries.addAction(
                    id = null,
                    // todo посмотреть
                    points = 200,
                    actionType = ActionType.DONE.name,
                    taskId = task.id
                )
            }
        }
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
        return queries.getTasks(listId).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getMyDayTasks(): Flow<List<Task>> {
        return queries.getMyDayTasks(DateTimeUtil.today().toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getTomorrowTasks(): Flow<List<Task>> {
        return queries.getTomorrowTasks(DateTimeUtil.tomorrow().toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getNextWeekTasks(): Flow<List<Task>> {
        return queries.getNextWeekTasks(DateTimeUtil.tomorrow().toLong(), DateTimeUtil.next(7).toLong()).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getIncomingTasks(): Flow<List<Task>> {
        return queries.getTasks(null).asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getFavoriteTasks(): Flow<List<Task>> {
        return queries.getFavoriteTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getDoneTasks(): Flow<List<Task>> {
        return queries.getDoneTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getDeletedTasks(): Flow<List<Task>> {
        return queries.getDeletedTasks().asFlow().mapToList().map { it.map { it.map() } }
    }

    override fun getAllTasks(): Flow<List<Task>> {
        return queries.getAllTasks().asFlow().mapToList().map { it.map { it.map() } }
    }
}