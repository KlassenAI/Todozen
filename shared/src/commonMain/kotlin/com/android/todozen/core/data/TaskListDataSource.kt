package com.android.todozen.core.data

import com.android.todozen.core.domain.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskListDataSource {
    suspend fun editTaskList(taskList: TaskList)
    suspend fun deleteTaskList(id: Long)
    suspend fun getTaskList(id: Long): TaskList
    fun getTaskLists(): Flow<List<TaskList>>
}