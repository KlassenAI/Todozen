package com.android.todozen.core.domain

import com.android.todozen.core.domain.PriorityType.*
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

object TaskUtil {

    fun getNextRepeatTask(task: Task): Task {
        val date = task.date!!.plus(1, DateTimeUnit.DAY)
        return task.copy(date = date)
    }

    fun getDoneAction(task: Task): Action {
        // todo изменить получение очков за завершение задачи, в зависимости от приоритета и прочих факторов
        val points = when(task.priority.type) {
            HIGH -> 80L
            MEDIUM -> 40L
            LOW -> 20L
            NO -> 10L
        }
        return Action(
            null,
            points = points,
            type = ActionType.DONE,
            task = task
        )
    }
}