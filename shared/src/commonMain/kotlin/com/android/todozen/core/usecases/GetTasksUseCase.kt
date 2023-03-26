package com.android.todozen.core.usecases

import com.android.todozen.core.data.TaskRepository
import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetTasksUseCase(private val taskRepository: TaskRepository) {

    suspend fun execute(list: TaskList): Flow<List<Task>> {
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
            else -> { flow {} }
        }
    }
}