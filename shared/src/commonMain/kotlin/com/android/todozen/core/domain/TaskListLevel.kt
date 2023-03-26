package com.android.todozen.core.domain

import kotlin.math.sqrt

data class TaskListLevel(
    val points: Long
) {
    val level: Int = ((sqrt(1.0 + 8.0 * points / 100.0) - 1.0) / 2.0).toInt()
    val pointsInCurrentLevel: Long = points - (100 * level * (level + 1) / 2)
    val pointsForNextLevel: Int = 100 * (level + 1)
}