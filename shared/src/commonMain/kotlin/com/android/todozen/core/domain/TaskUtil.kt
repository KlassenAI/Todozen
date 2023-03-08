package com.android.todozen.core.domain

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.plus

object TaskUtil {

    fun getNextRepeatTask(task: Task): Task {
        val date = task.date!!.plus(1, DateTimeUnit.DAY)
        return task.copy(date = date)
    }
}