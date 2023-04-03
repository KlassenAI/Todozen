package com.android.todozen.core.expect

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.java.KoinJavaComponent

private val context: Context by KoinJavaComponent.inject(Context::class.java)

actual fun getString(stringRes: StringResource): String {
    return StringDesc.Resource(stringRes).toString(context)
}

actual fun getString(stringRes: StringResource, int: Int): String {
    return StringDesc.ResourceFormatted(stringRes, int.toString()).toString(context)
}