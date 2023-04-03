package com.android.todozen.core.expect

import dev.icerock.moko.resources.StringResource

expect fun getString(stringRes: StringResource): String

expect fun getString(stringRes: StringResource, int: Int): String