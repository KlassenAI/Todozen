package com.android.todozen.core.di

import com.android.todozen.TaskDatabase
import com.android.todozen.action.TaskActionLocalSource
import com.android.todozen.action.TaskActionLocalSourceImpl
import com.android.todozen.action.TaskActionRepository
import com.android.todozen.core.data.*
import com.android.todozen.core.interactors.ListInteractor
import com.android.todozen.task.TaskInteractor
import com.android.todozen.core.usecases.*
import com.android.todozen.log.LogLocalSource
import com.android.todozen.log.LogLocalSourceImpl
import com.android.todozen.log.LogRepository
import com.android.todozen.log.LogInteractor
import com.android.todozen.task.TaskLocalSource
import com.android.todozen.task.TaskLocalSourceImpl
import com.android.todozen.task.TaskRepository
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val sharedModule = module {

    // Local Data Sources
    single<TaskLocalSource> { TaskLocalSourceImpl(TaskDatabase(get())) }
    single<ListDataSource> { ListDataSourceImpl(TaskDatabase(get())) }
    single<TaskActionLocalSource> { TaskActionLocalSourceImpl(TaskDatabase(get())) }
    single<LogLocalSource> { LogLocalSourceImpl(TaskDatabase(get())) }

    // Repositories
    single<TaskRepository> { TaskRepository(get()) }
    single<TaskActionRepository> { TaskActionRepository(get()) }
    single<ListRepository> { ListRepository(get()) }
    single<LogRepository> { LogRepository(get()) }

    // Logs
    single { LogInteractor(get()) }

    // Interactors
    single { ListInteractor(get()) }
    single { TaskInteractor(get(), get()) }
}