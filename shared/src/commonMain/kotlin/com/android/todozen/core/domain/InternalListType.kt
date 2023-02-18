package com.android.todozen.core.domain

enum class InternalListType(val id: Int) {
    ALL(1),
    MY_DAY(2),
    TOMORROW(3),
    NEXT_WEEK(4),
    INCOMING(5),
    FAVORITE(6),
    DONE(7),
    DELETED(8);

    fun isDateType(): Boolean = this == MY_DAY || this == TOMORROW || this == NEXT_WEEK

    companion object {

        val DEFAULT = ALL

        fun getById(id: Long) = values().firstOrNull { it.id.toLong() == id } ?: DEFAULT

        fun getByStr(str: String?) = str?.let { valueOf(str.uppercase()) } ?: DEFAULT
    }
}