package com.android.todozen.core.usecases

import com.android.todozen.core.data.TaskRepository
import com.android.todozen.core.domain.Task

class DeleteTaskUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(task: Task) {
        if (task.isDeleted) {
            taskRepository.deleteTask(task.id)
        } else {
            task.isDeleted = true
            taskRepository.updateTask(task)
        }
    }
}