package com.android.todozen.data

import com.android.todozen.domain.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskListDataSource {
    suspend fun editTaskList(taskList: TaskList)
    suspend fun deleteTaskList(id: Long)
    suspend fun getTaskList(id: Long): TaskList
    fun getAllTaskLists(): Flow<List<TaskList>>
}