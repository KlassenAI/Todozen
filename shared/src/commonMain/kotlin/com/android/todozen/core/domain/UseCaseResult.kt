package com.android.todozen.core.domain

sealed class UseCaseResult<T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error<T>(val error: Throwable) : UseCaseResult<T>()
}
