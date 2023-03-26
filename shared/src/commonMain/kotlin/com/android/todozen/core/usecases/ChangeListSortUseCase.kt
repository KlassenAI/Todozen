package com.android.todozen.core.usecases

import com.android.todozen.core.data.ListRepository
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.domain.Sort
import com.android.todozen.core.domain.TaskList

class ChangeListSortUseCase(private val listRepository: ListRepository) {

    suspend fun execute(list: TaskList, sort: Sort) {
        list.sort = sort
        when (list) {
            is EditableList -> listRepository.updateEditableList(list)
            is InternalList -> listRepository.updateInternalList(list)
        }
    }
}