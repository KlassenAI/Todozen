package com.android.todozen.features.actionlog

import dev.icerock.moko.parcelize.Parcelable
import dev.icerock.moko.parcelize.Parcelize

@Parcelize
data class ActionLogSpec(
    val taskCategoryId: Long? = 0
): Parcelable