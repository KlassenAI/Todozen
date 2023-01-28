package com.android.todozen.core.data

import com.android.todozen.core.domain.TaskList
import kotlinx.coroutines.flow.Flow

interface TaskListDataSource {
    suspend fun insertTaskList(taskList: TaskList)
    suspend fun updateTaskList(taskList: TaskList)
    suspend fun deleteTaskList(taskList: TaskList)
    suspend fun getTaskList(id: Long): TaskList
    suspend fun getTaskListsCount(): Long
    suspend fun updateTaskLists(taskLists: List<TaskList>)
    suspend fun getTaskLists(): List<TaskList>
    fun getFlowTaskLists(): Flow<List<TaskList>>
}