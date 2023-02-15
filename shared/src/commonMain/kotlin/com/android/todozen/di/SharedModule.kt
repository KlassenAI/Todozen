package com.android.todozen.di

import com.android.todozen.TaskDatabase
import com.android.todozen.core.data.TaskDataSourceImpl
import com.android.todozen.core.data.TaskDataSource
import com.android.todozen.core.data.ListDataSource
import com.android.todozen.core.data.ListDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val sharedModule = module {
    single<TaskDataSource> { TaskDataSourceImpl(TaskDatabase(get())) }
    single<ListDataSource> { ListDataSourceImpl(TaskDatabase(get())) }
}