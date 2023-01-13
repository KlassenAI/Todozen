package com.android.todozen.features.edittask

sealed class TaskSideEffect {
    object TaskAdded: TaskSideEffect()
}