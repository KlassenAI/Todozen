package com.android.todozen.core.data

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList

class ListRepository(private val listLocalSource: ListDataSource) {

    suspend fun getMyDayList() = listLocalSource.getMyDayList()

    suspend fun updateEditableList(list: EditableList) = listLocalSource.updateEditableList(list)

    suspend fun updateInternalList(list: InternalList) = listLocalSource.updateInternalList(list)
}