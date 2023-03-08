package com.android.todozen.core.domain

enum class RepeatType {
    NO, DAILY, WEEKLY, MONTHLY, YEARLY;

    companion object {
        val DEFAULT = NO
    }
}
