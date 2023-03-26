package com.android.todozen.core.usecases

import com.android.todozen.core.data.ActionRepository
import com.android.todozen.core.domain.*

class CreateCompleteTaskActionUseCase(private val actionRepository: ActionRepository) {

    suspend fun execute(task: Task): UseCaseResult<Action> {
        return if (task.isDone) {
            val completeTaskAction = getCompleteTaskAction(task)
            actionRepository.addAction(completeTaskAction)
            UseCaseResult.Success(completeTaskAction)
        } else {
            actionRepository.deleteAction(ActionType.DONE, task.id)
            UseCaseResult.Error(Exception(""))
        }
    }

    private fun getCompleteTaskAction(task: Task): Action {
        // todo изменить получение очков за завершение задачи, в зависимости от приоритета и прочих факторов
        val points = when(task.priority.type) {
            PriorityType.HIGH -> 80L
            PriorityType.MEDIUM -> 40L
            PriorityType.LOW -> 20L
            PriorityType.NO -> 10L
        }
        return Action(
            null,
            points = points,
            type = ActionType.DONE,
            task = task
        )
    }
}