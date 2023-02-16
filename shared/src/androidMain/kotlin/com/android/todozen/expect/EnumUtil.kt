package com.android.todozen.expect

import android.content.Context
import com.android.todozen.SharedRes.strings
import com.android.todozen.core.domain.InternalListType
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.core.domain.PriorityType
import com.android.todozen.core.domain.PriorityType.*
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.java.KoinJavaComponent.inject

private val context: Context by inject(Context::class.java)

actual fun PriorityType.getName(): String = when (this) {
    HIGH -> getString(strings.priority_high)
    MEDIUM -> StringDesc.Resource(strings.priority_medium).toString(context)
    LOW -> StringDesc.Resource(strings.priority_low).toString(context)
    NO -> StringDesc.Resource(strings.priority_default).toString(context)
}

actual fun InternalListType.getName(): String = when (this) {
    ALL -> StringDesc.Resource(strings.tasks_all).toString(context)
    MY_DAY -> StringDesc.Resource(strings.tasks_my_day).toString(context)
    TOMORROW -> StringDesc.Resource(strings.tasks_tomorrow).toString(context)
    NEXT_WEEK -> StringDesc.Resource(strings.tasks_next_week).toString(context)
    INCOMING -> StringDesc.Resource(strings.tasks_incoming).toString(context)
    FAVORITE -> StringDesc.Resource(strings.tasks_favorite).toString(context)
    DONE -> StringDesc.Resource(strings.tasks_done).toString(context)
    DELETED -> StringDesc.Resource(strings.tasks_deleted).toString(context)
}