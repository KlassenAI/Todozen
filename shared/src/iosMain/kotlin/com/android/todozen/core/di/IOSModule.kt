package com.android.todozen.core.di

import com.android.todozen.core.expect.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory().createDriver() }
}