package com.android.todozen.core.usecases

import com.android.todozen.core.domain.Sort

class GetSortsUseCase {

    fun execute() = Sort.values().toList()
}