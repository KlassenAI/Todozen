package com.android.todozen.features.fastedit

sealed class TaskSideEffect {
    object TaskAdded: TaskSideEffect()
}