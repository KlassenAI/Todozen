package com.android.todozen.task

import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.log.LogInteractor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

class TaskInteractor(
    private val taskRepository: TaskRepository,
    private val logInteractor: LogInteractor,
) {

    suspend fun checkTask(task: Task) {
        task.isDone = !task.isDone

        if (!task.isDone) {
            taskRepository.updateTask(task)
            logInteractor.logTaskCancelling(task)
            return
        }

        if (isTaskRepetitive(task)) {
            val nextRepetitiveTask = getNextRepeatTask(task)
            taskRepository.addTask(nextRepetitiveTask)
            task.repeat = RepeatType.DEFAULT
        }

        taskRepository.updateTask(task)
        logInteractor.logTaskCompleting(task)
    }

    private fun isTaskRepetitive(task: Task) = task.repeat != RepeatType.DEFAULT

    private fun getNextRepeatTask(task: Task): Task {
        val date = task.date!!.plus(1, DateTimeUnit.DAY)
        return task.copy(date = date)
    }

    suspend fun deleteTask(task: Task) {
        if (task.isDeleted) {
            taskRepository.deleteTask(task.id)
            logInteractor.logTaskDeleting(task)
        } else {
            task.isDeleted = true
            taskRepository.updateTask(task)
            logInteractor.logTaskArchiving(task)
        }
    }

    suspend fun restoreTask(task: Task) {
        task.isDeleted = false
        taskRepository.updateTask(task)
        logInteractor.logTaskRestoring(task)
    }

    suspend fun getTasksByList(list: TaskList): Flow<List<Task>> {
        return when (list) {
            is EditableList -> taskRepository.getTasks(list.id)
            is InternalList -> when (list.type) {
                ALL -> taskRepository.getAllTasks()
                MY_DAY -> taskRepository.getMyDayTasks()
                TOMORROW -> taskRepository.getAllTasks()
                NEXT_WEEK -> taskRepository.getAllTasks()
                INCOMING -> taskRepository.getAllTasks()
                FAVORITE -> taskRepository.getFavoriteTasks()
                DONE -> taskRepository.getAllTasks()
                DELETED -> taskRepository.getDeletedTasks()
            }
            else -> flow {}
        }
    }

}
