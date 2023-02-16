package com.android.todozen.expect

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

actual fun getString(stringRes: StringResource): String {
    return StringDesc.Resource(stringRes).localized()
}