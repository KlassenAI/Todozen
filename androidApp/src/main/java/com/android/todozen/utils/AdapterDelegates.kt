package com.android.todozen.utils

import com.android.todozen.databinding.ItemTaskBinding
import com.android.todozen.domain.ListItem
import com.android.todozen.domain.Task
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
    bind {
        binding.tvTitle.text = "${item.id} ${item.title}"
        binding.btnDone.isChecked = item.done
    }
}