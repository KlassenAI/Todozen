package com.android.todozen.expect

import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc

actual fun getString(stringRes: StringResource): String {
    return StringDesc.Resource(stringRes).localized()
}

actual fun getString(stringRes: StringResource, int: Int): String {
    return StringDesc.ResourceFormatted(stringRes, int.toString()).localized()
}