package com.android.todozen.core.data

import com.android.todozen.core.domain.Priority
import com.android.todozen.core.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    suspend fun insertTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun getTask(id: Long): Task
    suspend fun getPriorities(): List<Priority>
    fun getTasks(listId: Long?): Flow<List<Task>>
    fun getTasksForToday(): Flow<List<Task>>
    fun getFavoriteTasks(): Flow<List<Task>>
    fun getDeletedTasks(): Flow<List<Task>>
    fun getAllTasks(): Flow<List<Task>>
}