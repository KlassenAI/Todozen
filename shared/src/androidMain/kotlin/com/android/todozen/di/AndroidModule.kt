package com.android.todozen.di

import android.content.Context
import com.android.todozen.expect.DatabaseDriverFactory
import org.koin.core.module.Module
import org.koin.dsl.koinApplication
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single { DatabaseDriverFactory(get()).createDriver() }
}