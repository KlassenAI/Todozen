package com.android.todozen.di

import com.android.todozen.features.editdate.EditDateViewModel
import com.android.todozen.features.edittask.EditTaskViewModel
import com.android.todozen.features.edittasklist.EditTaskListViewModel
import com.android.todozen.features.menu.MenuViewModel
import com.android.todozen.features.tasklist.TaskListViewModel
import org.koin.dsl.module

val appModule = module {
    single { EditTaskViewModel(get()) }
    single { TaskListViewModel(get()) }
    single { EditDateViewModel(get()) }
    single { MenuViewModel(get()) }
    single { EditTaskListViewModel(get()) }
}