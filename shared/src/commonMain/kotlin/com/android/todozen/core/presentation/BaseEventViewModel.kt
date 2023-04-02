package com.android.todozen.core.presentation

import com.android.todozen.core.presentation.BaseEventViewModel.BaseEventListener
import com.android.todozen.core.presentation.BaseViewModel.BaseViewModelState
import dev.icerock.moko.mvvm.dispatcher.EventsDispatcher

abstract class BaseEventViewModel<S : BaseViewModelState, E : BaseEventListener> :
    BaseViewModel<S>() {

    abstract val eventsDispatcher: EventsDispatcher<E>

    interface BaseEventListener
}