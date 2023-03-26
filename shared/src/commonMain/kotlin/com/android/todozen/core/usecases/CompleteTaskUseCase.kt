package com.android.todozen.core.usecases

import com.android.todozen.core.data.TaskRepository
import com.android.todozen.core.domain.RepeatType
import com.android.todozen.core.domain.Task

class CompleteTaskUseCase(
    private val taskRepository: TaskRepository
) {

    suspend fun execute(task: Task) {
        // изменить состояние выполнения и очищаем повторяемость, если задача выполнена
        task.isDone = !task.isDone
        if (task.isDone) {
            task.repeat = RepeatType.NO
        }

        // сохраняем задачу в базе данных
        taskRepository.updateTask(task)
    }
}