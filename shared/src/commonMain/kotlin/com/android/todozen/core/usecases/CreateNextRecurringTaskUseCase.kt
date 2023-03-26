package com.android.todozen.core.usecases

import com.android.todozen.core.data.TaskRepository
import com.android.todozen.core.domain.RepeatType
import com.android.todozen.core.domain.Task
import com.android.todozen.core.domain.TaskUtil
import com.android.todozen.core.domain.UseCaseResult

class CreateNextRecurringTaskUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(task: Task): UseCaseResult<Unit> {
        val isRecurringTaskDone = isRecurringTaskDone(task)
        if (isRecurringTaskDone) {
            taskRepository.addTask(TaskUtil.getNextRepeatTask(task))
            return UseCaseResult.Success(Unit)
        }
        return UseCaseResult.Error(Exception(""))
    }

    private fun isRecurringTaskDone(task: Task): Boolean {
        return !task.isDone && task.repeat != RepeatType.DEFAULT
    }
}