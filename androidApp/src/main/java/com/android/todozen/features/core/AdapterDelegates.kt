package com.android.todozen.features.core

import com.android.todozen.databinding.ItemTaskBinding
import com.android.todozen.databinding.ItemTaskListBinding
import com.android.todozen.utils.DateTimeUtil.formatDateTime
import com.android.todozen.domain.Task
import com.android.todozen.domain.TaskList
import com.android.todozen.utils.ListItem
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
        binding.tvTitle.text = "${item.id} ${item.title} ${formatDateTime(item.date, item.time)}"
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
    binding.btnDelete.setOnClickListener { deleteListener.invoke(item) }
    bind { payloads ->
        binding.tvTitle.text = "${item.id} ${item.title}"
    }
}
