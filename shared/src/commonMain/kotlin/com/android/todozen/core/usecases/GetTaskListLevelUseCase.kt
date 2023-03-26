package com.android.todozen.core.usecases

import com.android.todozen.core.data.ActionRepository
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.domain.TaskListLevel

class GetTaskListLevelUseCase(private val actionRepository: ActionRepository) {

    suspend fun execute(list: TaskList): TaskListLevel {
        val points = when (list) {
            is EditableList -> actionRepository.getListPoints(list.id)
            is InternalList -> actionRepository.getGeneralPoints()
            else -> 0L
        }
        return TaskListLevel(points)
    }
}