package com.android.todozen.core.data

import com.android.todozen.core.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    suspend fun editTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun getTask(id: Long): Task
    fun getTasks(listId: Long?): Flow<List<Task>>
    fun getAllTasks(): Flow<List<Task>>
    fun getTasksForToday(): Flow<List<Task>>
}