package com.android.todozen.core.domain

import com.android.todozen.SharedRes
import com.android.todozen.core.expect.getString
import kotlinx.datetime.*
import kotlinx.datetime.DayOfWeek.*

object DateTimeUtil {

    fun now() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    private fun dayBeforeYesterday(): LocalDate = now().date.minus(2, DateTimeUnit.DAY)
    private fun yesterday(): LocalDate = now().date.minus(1, DateTimeUnit.DAY)
    fun today(): LocalDate = now().date
    fun tomorrow(): LocalDate = now().date.plus(1, DateTimeUnit.DAY)
    private fun dayAfterTomorrow(): LocalDate = now().date.plus(2, DateTimeUnit.DAY)

    fun next(plus: Int): LocalDate = now().date.plus(plus, DateTimeUnit.DAY)

    fun formatDateTime(date: LocalDate?, time: LocalTime?): String? {
        if (date == null) return null
        val strDate = formatDate(date)
        if (time == null) return strDate
        return buildString {
            append(formatTime(time))
            append(", ")
            append(strDate)
        }
    }

    private fun formatDate(date: LocalDate?): String = when {
        date == null -> ""
        date.year < now().year -> buildString {
            append(date.dayOfMonth)
            append(" ")
            append(date.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() })
            append(" ")
            append(date.year)
            append(", ")
            append(getString(SharedRes.strings.date_days_back, date.daysUntil(now().date)))
        }
        date.year > now().year -> buildString {
            append(date.dayOfMonth)
            append(" ")
            append(date.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() })
            append(" ")
            append(date.year)
            append(", ")
            append(getString(SharedRes.strings.date_days_next, now().date.daysUntil(date)))
        }
        date < dayBeforeYesterday() -> buildString {
            append(date.dayOfMonth)
            append(" ")
            append(date.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() })
            append(", ")
            append(getString(SharedRes.strings.date_days_back, date.daysUntil(now().date)))
        }
        date == dayBeforeYesterday() -> getString(SharedRes.strings.date_day_before_yesterday)
        date == yesterday() -> getString(SharedRes.strings.date_today)
        date == today() -> getString(SharedRes.strings.date_today)
        date == tomorrow() -> getString(SharedRes.strings.date_tomorrow)
        date == dayAfterTomorrow() -> getString(SharedRes.strings.date_day_after_tomorrow)
        date < next(10) -> when(date.dayOfWeek) {
            MONDAY -> getString(SharedRes.strings.date_day_next_monday)
            TUESDAY -> getString(SharedRes.strings.date_day_next_tuesday)
            WEDNESDAY -> getString(SharedRes.strings.date_day_next_wednesday)
            THURSDAY -> getString(SharedRes.strings.date_day_next_thursday)
            FRIDAY -> getString(SharedRes.strings.date_day_next_friday)
            SATURDAY -> getString(SharedRes.strings.date_day_next_saturday)
            SUNDAY -> getString(SharedRes.strings.date_day_next_sunday)
            else -> ""
        }
        else -> buildString {
            append(date.dayOfMonth)
            append(" ")
            append(date.month.name.lowercase().take(3).replaceFirstChar { it.uppercase() })
            append(", ")
            append(getString(SharedRes.strings.date_days_next, now().date.daysUntil(date)))
        }
    }

    fun formatTime(time: LocalTime?): String {
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