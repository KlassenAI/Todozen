package com.android.todozen.core.domain

enum class Sort(val id: Int) {
    TITLE(1),
    DATE(2),
    LIST(3),
    PRIORITY(4),
    LABEL(5),
    ARBITRARY(6);

    companion object {

        val DEFAULT = TITLE

        fun getById(id: Long) = values().firstOrNull { it.id.toLong() == id } ?: DEFAULT

        fun getByStr(str: String?) = str?.let { valueOf(str.uppercase()) } ?: DEFAULT
    }
}