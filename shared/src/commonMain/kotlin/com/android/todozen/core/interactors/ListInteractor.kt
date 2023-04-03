package com.android.todozen.core.interactors

import com.android.todozen.core.data.ListRepository
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.domain.Sort
import com.android.todozen.core.domain.TaskList

class ListInteractor(private val listRepository: ListRepository) {

    suspend fun getMyDayList() = listRepository.getMyDayList()

    fun getListSorts() = Sort.values().toList()

    suspend fun changeListSort(list: TaskList, sort: Sort) {
        list.sort = sort
        when (list) {
            is EditableList -> listRepository.updateEditableList(list)
            is InternalList -> listRepository.updateInternalList(list)
        }
    }
}