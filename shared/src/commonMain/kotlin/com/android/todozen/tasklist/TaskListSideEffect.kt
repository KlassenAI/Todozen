package com.android.todozen.tasklist

sealed class TaskListSideEffect {
    data class Toast(val message: String): TaskListSideEffect()
    object Success: TaskListSideEffect()
    data class Error(val error: Throwable): TaskListSideEffect()
}