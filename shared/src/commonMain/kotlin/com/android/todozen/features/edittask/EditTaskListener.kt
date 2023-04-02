package com.android.todozen.features.edittask

import com.android.todozen.core.domain.Priority

interface EditTaskListener {
    fun showPriorities(priorities: List<Priority>)
}