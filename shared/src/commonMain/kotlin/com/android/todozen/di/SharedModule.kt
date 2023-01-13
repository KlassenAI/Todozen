package com.android.todozen.di

import com.android.todozen.TaskDatabase
import com.android.todozen.data.TaskDataSourceImpl
import com.android.todozen.data.TaskDataSource
import com.android.todozen.data.TaskListDataSource
import com.android.todozen.data.TaskListDataSourceImpl
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val sharedModule = module {
    single<TaskDataSource> { TaskDataSourceImpl(TaskDatabase(get())) }
    single<TaskListDataSource> { TaskListDataSourceImpl(TaskDatabase(get())) }
}