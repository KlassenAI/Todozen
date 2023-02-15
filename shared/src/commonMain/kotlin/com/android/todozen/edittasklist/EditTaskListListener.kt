package com.android.todozen.edittasklist

import com.android.todozen.core.domain.Sort

interface EditTaskListListener {
    fun showSorts(sorts: List<Sort>)
}