package com.android.todozen.core.domain

data class Priority(
    var id: Long? = null,
    val type: String? = "",
    val color: Int? = null,
)
