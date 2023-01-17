package com.android.todozen.edittask

sealed class TaskSideEffect {
    object TaskAdded: TaskSideEffect()
}