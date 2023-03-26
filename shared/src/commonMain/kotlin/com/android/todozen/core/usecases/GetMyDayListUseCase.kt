package com.android.todozen.core.usecases

import com.android.todozen.core.data.ListRepository

class GetMyDayListUseCase(private val listRepository: ListRepository) {

    suspend fun execute() = listRepository.getMyDayList()
}