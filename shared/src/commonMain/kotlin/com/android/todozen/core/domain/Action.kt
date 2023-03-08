package com.android.todozen.core.domain

data class Action(
    val id: Long? = null,
    val points: Long,
    val type: ActionType,
    val task: Task?,
)
