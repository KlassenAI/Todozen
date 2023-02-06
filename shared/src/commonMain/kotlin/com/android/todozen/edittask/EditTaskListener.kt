package com.android.todozen.edittask

import com.android.todozen.core.domain.Priority

interface EditTaskListener {
    fun showPriorities(priorities: List<Priority>)
}