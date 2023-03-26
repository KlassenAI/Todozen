package com.android.todozen.di

import com.android.todozen.TaskDatabase
import com.android.todozen.core.data.*
import com.android.todozen.core.usecases.*
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun platformModule(): Module

val sharedModule = module {
    single<TaskDataSource> { TaskDataSourceImpl(TaskDatabase(get())) }
    single<ListDataSource> { ListDataSourceImpl(TaskDatabase(get())) }
    single<ActionDataSource> { ActionDataSourceImpl(TaskDatabase(get())) }


    single<TaskRepository> { TaskRepository(get()) }
    single<ActionRepository> { ActionRepository(get()) }
    single<ListRepository> { ListRepository(get()) }

    single { ChangeListSortUseCase(get()) }
    single { CompleteTaskUseCase(get()) }
    single { CreateCompleteTaskActionUseCase(get()) }
    single { CreateNextRecurringTaskUseCase(get()) }
    single { DeleteTaskUseCase(get()) }
    single { GetTaskListLevelUseCase(get()) }
    single { GetMyDayListUseCase(get()) }
    single { GetSortsUseCase() }
    single { GetTasksUseCase(get()) }
    single { GetActionsUseCase(get()) }
}