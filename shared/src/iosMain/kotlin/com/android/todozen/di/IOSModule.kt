package com.android.todozen.di

import com.android.todozen.expect.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory().createDriver() }
}