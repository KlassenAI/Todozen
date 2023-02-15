package com.android.todozen.core.data

import com.android.todozen.core.domain.Priority
import com.android.todozen.core.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {

    suspend fun insertTask(task: Task)
    suspend fun getTask(id: Long): Task
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)

    suspend fun getPriorities(): List<Priority>

    fun getTasks(listId: Long?): Flow<List<Task>>
    fun getMyDayTasks(): Flow<List<Task>>
    fun getTomorrowTasks(): Flow<List<Task>>
    fun getNextWeekTasks(): Flow<List<Task>>
    fun getIncomingTasks(): Flow<List<Task>>
    fun getFavoriteTasks(): Flow<List<Task>>
    fun getDoneTasks(): Flow<List<Task>>
    fun getDeletedTasks(): Flow<List<Task>>
    fun getAllTasks(): Flow<List<Task>>
}