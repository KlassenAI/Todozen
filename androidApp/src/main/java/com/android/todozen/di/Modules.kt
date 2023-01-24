package com.android.todozen.di

import com.android.todozen.editdate.EditDateViewModel
import com.android.todozen.edittask.EditTaskViewModel
import com.android.todozen.edittasklist.EditTaskListViewModel
import com.android.todozen.menu.MenuViewModel
import com.android.todozen.tasklist.TaskListViewModel
import org.koin.dsl.module

val appModule = module {
    single { EditTaskViewModel(get()) }
    single { TaskListViewModel(get()) }
    single { EditDateViewModel(get()) }
    single { MenuViewModel(get()) }
    single { EditTaskListViewModel(get()) }
}