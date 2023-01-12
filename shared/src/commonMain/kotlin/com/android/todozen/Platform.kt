package com.android.todozen

interface Platform {
    val name: String
}

expect fun  getPlatform(): Platform