package com.android.todozen.features.actionlog

import com.android.todozen.core.domain.DateTimeUtil
import com.android.todozen.core.domain.Task
import com.android.todozen.features.actionlog.TaskLogType.*

class LogsInteractor(
    private val taskLogRepository: ActionLogRepository
) {

    suspend fun logTaskCreating(task: Task) = taskLogRepository.logTask(createLog(task, CREATE))
    suspend fun logTaskUpdating(task: Task) = taskLogRepository.logTask(createLog(task, UPDATE))
    suspend fun logTaskCompleting(task: Task) = taskLogRepository.logTask(createLog(task, COMPLETE))
    suspend fun logTaskCanceling(task: Task) = taskLogRepository.logTask(createLog(task, CANCEL))
    suspend fun logTaskArchiving(task: Task) = taskLogRepository.logTask(createLog(task, ARCHIVE))
    suspend fun logTaskDeleting(task: Task) = taskLogRepository.logTask(createLog(task, DELETE))

    suspend fun getTaskLogs(categoryId: Long? = 0L): List<TaskLog> {
        return if (categoryId == 0L) {
            taskLogRepository.getAllTaskLogs()
        } else {
            taskLogRepository.getTaskLogsByCategory(categoryId)
        }
    }

    private fun createLog(task: Task, type: TaskLogType) = TaskLog(
        id = 0,
        type = type,
        date = DateTimeUtil.now(),
        taskId = task.id,
        taskTitle = task.title,
        taskCategoryId = task.list?.id
    )
}