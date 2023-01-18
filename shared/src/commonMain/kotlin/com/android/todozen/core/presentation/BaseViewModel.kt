package com.android.todozen.core.presentation

import dev.icerock.moko.mvvm.livedata.LiveData
import dev.icerock.moko.mvvm.livedata.MutableLiveData
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseState>: ViewModel() {

    protected val _state = MutableLiveData(initialState())
    val state: LiveData<S> get() = _state

    abstract fun initialState(): S

    protected fun updateState(update: S.() -> S) {
        _state.value = update(_state.value)
    }

    fun clearState() = updateState { initialState() }

    protected fun doJob(block: suspend CoroutineScope.() -> Unit): Job {
        return viewModelScope.launch { block() }
    }
}