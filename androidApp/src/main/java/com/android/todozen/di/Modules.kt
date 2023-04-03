package com.android.todozen.di

import com.android.todozen.features.actionlog.ActionLogViewModel
import com.android.todozen.features.editdate.EditDateViewModel
import com.android.todozen.features.edittask.EditTaskListener
import com.android.todozen.features.edittask.EditTaskViewModel
import com.android.todozen.features.edittasklist.EditTaskListViewModel
import com.android.todozen.features.menu.MenuViewModel
import com.android.todozen.features.tasklist.TaskListViewModel
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher
import org.koin.dsl.module

val appModule = module {

    single { EditTaskViewModel(get(), get(), EventsDispatcher<EditTaskListener>()) }

    single { EventsDispatcher<TaskListViewModel.EventsListener>() }
    single { TaskListViewModel(get(), get(), get()) }

    single { EditDateViewModel(get()) }
    single { MenuViewModel(get()) }
    single { EditTaskListViewModel(get()) }

    single { ActionLogViewModel(get()) }
}