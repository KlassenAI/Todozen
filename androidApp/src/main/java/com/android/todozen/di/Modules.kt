package com.android.todozen.di

import com.android.todozen.features.fastedit.TaskViewModel
import com.android.todozen.features.tasklist.TaskListViewModel
import org.koin.dsl.module

val appModule = module {
    single { TaskViewModel(get()) }
    single { TaskListViewModel(get()) }
}