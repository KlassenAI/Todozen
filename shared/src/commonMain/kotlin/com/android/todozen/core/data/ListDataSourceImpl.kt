package com.android.todozen.core.data

import com.android.todozen.TaskDatabase
import com.android.todozen.core.domain.*
import com.android.todozen.core.domain.InternalListType.*

class ListDataSourceImpl(db: TaskDatabase) : ListDataSource {

    private val queries = db.taskQueries

    override suspend fun insertEditableList(list: EditableList) {
        queries.insertEditableList(
            id = null,
            title = list.title,
            isFavorite = list.isFavorite,
            color = list.color?.toLong(),
            position = list.position,
            sortId = list.sort.id.toLong()
        )
    }

    override suspend fun updateEditableList(list: EditableList) {
        updateQueryTaskList(list)
    }

    override suspend fun deleteEditableList(list: EditableList) {
        queries.transaction {
            queries.deleteEditableList(list.id!!)
            queries.reduceEditableListPositions(list.position)
        }
    }

    override suspend fun getEditableList(id: Long): EditableList {
        return queries.getEditableList(id).executeAsOne().map()
    }

    override suspend fun getEditableListsCount(): Long =
        queries.getEditableListsCount().executeAsOne()

    override suspend fun updateEditableLists(lists: List<EditableList>) {
        return queries.transaction {
            lists.forEach { updateQueryTaskList(it) }
        }
    }

    private fun updateQueryTaskList(list: EditableList) {
        queries.updateEditableList(
            id = list.id,
            title = list.title,
            isFavorite = list.isFavorite,
            color = list.color?.toLong(),
            position = list.position,
            sortId = list.sort.id.toLong()
        )
    }

    override suspend fun getEditableLists(): List<EditableList> {
        return queries.getAllEditableLists().executeAsList().map { it.map() }
    }

    override suspend fun updateInternalList(list: InternalList) {
        return queries.updateInternalList(
            id = list.type.id.toLong(),
            title = list.type.name,
            position = list.position.toLong(),
            sortId = list.sort.id.toLong()
        )
    }

    override suspend fun getInternalLists(): List<InternalList> {
        return queries.transactionWithResult {
            val lists = queries.getInternalLists().executeAsList().map { it.map() }
            lists.forEach {
                it.taskCount = when (it.type) {
                    ALL -> queries.getCountAllTasks().executeAsOne().toInt()
                    MY_DAY -> queries.getCountMyDayTasks(DateTimeUtil.today().toLong()).executeAsOne().toInt()
                    TOMORROW -> queries.getCountTomorrowTasks(DateTimeUtil.tomorrow().toLong()).executeAsOne().toInt()
                    NEXT_WEEK -> queries.getCountNextWeekTasks(DateTimeUtil.tomorrow().toLong(), DateTimeUtil.next(7).toLong()).executeAsOne().toInt()
                    INCOMING -> queries.getCountTasks(null).executeAsOne().toInt()
                    FAVORITE -> queries.getCountFavoriteTasks().executeAsOne().toInt()
                    DONE -> queries.getCountDoneTasks().executeAsOne().toInt()
                    DELETED -> queries.getCountDeletedTasks().executeAsOne().toInt()
                }
            }
            return@transactionWithResult lists
        }
    }

    override suspend fun getMyDayList(): InternalList {
        return queries.getMyDayList(MY_DAY.id.toLong()).executeAsOne().map()
    }
}