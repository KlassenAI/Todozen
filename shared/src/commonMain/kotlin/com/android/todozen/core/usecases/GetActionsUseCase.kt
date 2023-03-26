package com.android.todozen.core.usecases

import com.android.todozen.core.data.ActionRepository

class GetActionsUseCase(private val actionRepository: ActionRepository) {

    suspend fun execute() = actionRepository.getActions()

}