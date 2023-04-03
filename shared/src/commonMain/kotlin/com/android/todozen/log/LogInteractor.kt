package com.android.todozen.log

import com.android.todozen.core.domain.*
import com.android.todozen.log.TaskLogType.*
import com.android.todozen.task.Task
import database.TaskLogEntity
import kotlinx.datetime.LocalDate

class LogInteractor(
    private val logRepository: LogRepository
) {

    suspend fun logTaskCreating(task: Task) = logRepository.logTask(createLog(task, CREATE))
    suspend fun logTaskUpdating(task: Task) = logRepository.logTask(createLog(task, UPDATE))
    suspend fun logTaskCompleting(task: Task) = logRepository.logTask(createLog(task, COMPLETE))
    suspend fun logTaskCancelling(task: Task) = logRepository.logTask(createLog(task, CANCEL))
    suspend fun logTaskArchiving(task: Task) = logRepository.logTask(createLog(task, ARCHIVE))
    suspend fun logTaskRestoring(task: Task) = logRepository.logTask(createLog(task, RESTORE))
    suspend fun logTaskDeleting(task: Task) = logRepository.logTask(createLog(task, DELETE))

    private fun createLog(task: Task, type: TaskLogType) = TaskLogData(
        type = type,
        date = DateTimeUtil.now(),
        taskId = task.id,
        taskTitle = task.title,
        taskCategoryId = task.list?.id
    )

    suspend fun getTaskLogs(categoryId: Long? = 0L): List<TaskLogItem> {
        return if (categoryId == 0L) {
            logRepository.getAllTaskLogs().getGroupedTaskLogs()
        } else {
            logRepository.getTaskLogsByCategory(categoryId).getGroupedTaskLogs()
        }
    }

    private fun List<TaskLogEntity>.getGroupedTaskLogs(): List<TaskLogItem> {
        val listItems = mutableListOf<TaskLogItem>()
        groupBy { it.dateTime.toLocalDateTime().date }.forEach { (header, taskLogs) ->
            listItems.add(header.map())
            listItems.addAll(taskLogs.map { it.map() })
        }
        return listItems.toList()
    }

    private fun LocalDate.map(): TaskLogHeaderListItem = TaskLogHeaderListItem(
        header = if (year == DateTimeUtil.now().year) {
            "$dayOfMonth ${month.getString()} ${dayOfWeek.getString()}"
        } else {
            "$dayOfMonth ${month.getString()} $year ${dayOfWeek.getString()}"
        }
    )

    private fun TaskLogEntity.map(): TaskLogListItem = TaskLogListItem(
        id = taskLogId,
        type = valueOf(taskLogType),
        time = dateTime.toLocalDateTime().time.getString(),
        taskId = taskId,
        taskTitle = taskTitle,
        taskCategoryId = taskCategoryId,
    )
}