package com.android.todozen.expect

import android.content.Context
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.java.KoinJavaComponent

private val context: Context by KoinJavaComponent.inject(Context::class.java)

actual fun getString(stringRes: StringResource): String {
    return StringDesc.Resource(stringRes).toString(context)
}