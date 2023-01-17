package com.android.todozen.core.domain

import kotlinx.datetime.*

object DateTimeUtil {

    fun now(): LocalDateTime {
        return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    }

    fun formatDateTime(date: LocalDate?, time: LocalTime?): String? {
        if (date == null) return null
        val stringBuilder = StringBuilder(formatDateOrEmpty(date))
        if (time == null) return stringBuilder.toString()
        stringBuilder.append(", ")
        stringBuilder.append(formatTimeOrEmpty(time))
        return stringBuilder.toString()
    }

    private fun formatDateOrEmpty(date: LocalDate?): String {
        if (date == null) return ""
        val month = date.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() }
        val day = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else date.dayOfMonth
        val year = date.year
        return buildString {
            append(month)
            append(" ")
            append(day)
            append(" ")
            append(year)
        }
    }

    fun formatTimeOrEmpty(time: LocalTime?): String {
        if (time == null) return ""
        val hour = if (time.hour < 10) "0${time.hour}" else time.hour
        val minute = if (time.minute < 10) "0${time.minute}" else time.minute

        return buildString {
            append(hour)
            append(":")
            append(minute)
        }
    }
}