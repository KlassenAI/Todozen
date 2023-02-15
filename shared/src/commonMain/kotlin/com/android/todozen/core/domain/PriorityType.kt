package com.android.todozen.core.domain

enum class PriorityType(val id: Long) {
    HIGH(1),
    MEDIUM(2),
    LOW(3),
    NO(4);

    companion object {
        val DEFAULT = NO
    }
}
