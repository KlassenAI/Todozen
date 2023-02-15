package com.android.todozen.core.data

import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList

interface ListDataSource {
    suspend fun insertEditableList(list: EditableList)
    suspend fun updateEditableList(list: EditableList)
    suspend fun deleteEditableList(list: EditableList)
    suspend fun getEditableList(id: Long): EditableList
    suspend fun getEditableListsCount(): Long
    suspend fun updateEditableLists(lists: List<EditableList>)
    suspend fun getEditableLists(): List<EditableList>

    suspend fun updateInternalList(list: InternalList)
    suspend fun getInternalLists(): List<InternalList>
    suspend fun getMyDayList(): InternalList
}