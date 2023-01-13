package com.android.todozen.data

import com.android.todozen.domain.Task
import kotlinx.coroutines.flow.Flow

interface TaskDataSource {
    suspend fun addTask(task: Task)
    suspend fun updateTask(task: Task)
    suspend fun deleteTask(id: Long)
    suspend fun getTask(id: Long): Task
    fun getAllTasks(): Flow<List<Task>>
}