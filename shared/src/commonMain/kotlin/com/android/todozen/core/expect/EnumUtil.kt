package com.android.todozen.core.expect

import com.android.todozen.core.domain.InternalListType
import com.android.todozen.core.domain.PriorityType

expect fun PriorityType.getName(): String

expect fun InternalListType.getName(): String
