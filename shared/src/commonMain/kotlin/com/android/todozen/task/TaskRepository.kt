package com.android.todozen.task

class TaskRepository(private val taskLocalSource: TaskLocalSource) {

    suspend fun addTask(task: Task) = taskLocalSource.insertTask(task)

    suspend fun updateTask(task: Task) = taskLocalSource.updateTask(task)

    suspend fun deleteTask(taskId: Long) = taskLocalSource.deleteTask(taskId)

    suspend fun getTasks(listId: Long?) = taskLocalSource.getTasks(listId)
    suspend fun getMyDayTasks() = taskLocalSource.getMyDayTasks()
    suspend fun getTomorrowTasks() = taskLocalSource.getTomorrowTasks()
    suspend fun getNextWeekTasks() = taskLocalSource.getNextWeekTasks()
    suspend fun getIncomingTasks() = taskLocalSource.getIncomingTasks()
    suspend fun getFavoriteTasks() = taskLocalSource.getFavoriteTasks()
    suspend fun getDoneTasks() = taskLocalSource.getDoneTasks()
    suspend fun getDeletedTasks() = taskLocalSource.getDeletedTasks()
    suspend fun getAllTasks() = taskLocalSource.getAllTasks()
}