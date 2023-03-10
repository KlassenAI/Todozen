package com.android.todozen.core.data

import com.android.todozen.core.domain.*

class TaskRepository(
    private val taskSource: TaskDataSource,
    private val actionSource: ActionDataSource
) {

    suspend fun checkTask(task: Task) {

        // создаем следующее задание, если задача выполнена и есть повторение
        if (!task.isDone && task.repeat != RepeatType.DEFAULT) {
            taskSource.insertTask(TaskUtil.getNextRepeatTask(task))
        }

        // изменить состояние выполнения и очищаем повторяемость, если задача выполнена
        task.isDone = !task.isDone
        if (task.isDone) {
            task.repeat = RepeatType.NO
        }

        // сохраняем задачу в базе данных
        taskSource.updateTask(task)

        // добавляем действие о выполнении, если задача была выполнена
        if (task.isDone) {
            actionSource.addAction(TaskUtil.getDoneAction(task))
        } else {
            actionSource.deleteAction(ActionType.DONE, task.id)
        }
    }

    suspend fun getListPoints(listId: Long): Long {
        return actionSource.getListPoints(listId)
    }
}