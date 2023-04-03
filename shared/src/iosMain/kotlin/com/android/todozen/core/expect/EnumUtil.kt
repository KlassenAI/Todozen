package com.android.todozen.core.expect

import com.android.todozen.SharedRes.strings
import com.android.todozen.core.domain.InternalListType
import com.android.todozen.core.domain.InternalListType.*
import com.android.todozen.core.domain.PriorityType
import com.android.todozen.core.domain.PriorityType.*
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun PriorityType.getName(): String = when (this) {
    HIGH -> StringDesc.Resource(strings.priority_high).localized()
    MEDIUM -> StringDesc.Resource(strings.priority_medium).localized()
    LOW -> StringDesc.Resource(strings.priority_low).localized()
    NO -> StringDesc.Resource(strings.priority_default).localized()
}

actual fun InternalListType.getName(): String = when (this) {
    ALL -> StringDesc.Resource(strings.tasks_all).localized()
    MY_DAY -> StringDesc.Resource(strings.tasks_my_day).localized()
    TOMORROW -> StringDesc.Resource(strings.tasks_tomorrow).localized()
    NEXT_WEEK -> StringDesc.Resource(strings.tasks_next_week).localized()
    INCOMING -> StringDesc.Resource(strings.tasks_incoming).localized()
    FAVORITE -> StringDesc.Resource(strings.tasks_favorite).localized()
    DONE -> StringDesc.Resource(strings.tasks_done).localized()
    DELETED -> StringDesc.Resource(strings.tasks_deleted).localized()
}
