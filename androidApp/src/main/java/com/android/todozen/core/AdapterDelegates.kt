package com.android.todozen.core

import com.android.todozen.databinding.ItemTaskBinding
import com.android.todozen.databinding.ItemTaskList2Binding
import com.android.todozen.databinding.ItemTaskListBinding
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.core.domain.Task
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.domain.ListItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun tasksAdapterDelegate(
    clickListener: (Task) -> Unit,
    checkListener: (Task) -> Unit,
    deleteListener: (Task) -> Unit
) = adapterDelegateViewBinding<Task, ListItem, ItemTaskBinding>(
    { inflater, root -> ItemTaskBinding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    binding.btnDone.setOnClickListener { checkListener.invoke(item) }
    binding.btnDelete.setOnClickListener { deleteListener.invoke(item) }
    bind { payloads ->
        binding.tvTitle.text = item.title
        binding.tvDate.init(formatDateTime(item.date, item.time)) { text = it }
        binding.tvList.init(item.listId) { text = item.listTitle }
        binding.btnDone.isChecked = item.done
    }
}

fun taskListsAdapterDelegate(
    clickListener: (TaskList) -> Unit,
    deleteListener: ((TaskList) -> Unit)? = null
) = adapterDelegateViewBinding<TaskList, ListItem, ItemTaskListBinding>(
    { inflater, root -> ItemTaskListBinding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    binding.btnDelete.setOnClickListener { deleteListener?.invoke(item) }
    bind { payloads ->
        binding.tvTitle.text = "${item.id} ${item.title}"
    }
}

fun taskListsAdapterDelegate(
    currentTaskListId: Long?,
    clickListener: (TaskList) -> Unit,
) = adapterDelegateViewBinding<TaskList, ListItem, ItemTaskList2Binding>(
    { inflater, root -> ItemTaskList2Binding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    bind { payloads ->
        binding.tvTitle.text = "${item.id == currentTaskListId} ${item.id} ${item.title}"
    }
}
