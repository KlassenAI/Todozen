package com.android.todozen.task

import com.android.todozen.core.domain.Priority
import kotlinx.coroutines.flow.Flow

interface TaskLocalSource {

    suspend fun insertTask(task: Task): Long
    suspend fun getTask(id: Long): Task
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)

    suspend fun getPriorities(): List<Priority>

    suspend fun getTasks(listId: Long?): Flow<List<Task>>
    suspend fun getMyDayTasks(): Flow<List<Task>>
    suspend fun getTomorrowTasks(): Flow<List<Task>>
    suspend fun getNextWeekTasks(): Flow<List<Task>>
    suspend fun getIncomingTasks(): Flow<List<Task>>
    suspend fun getFavoriteTasks(): Flow<List<Task>>
    suspend fun getDoneTasks(): Flow<List<Task>>
    suspend fun getDeletedTasks(): Flow<List<Task>>
    suspend fun getAllTasks(): Flow<List<Task>>
}