package com.android.todozen.core

import android.content.res.ColorStateList
import com.android.todozen.R
import com.android.todozen.databinding.ItemTaskBinding
import com.android.todozen.databinding.ItemTaskList2Binding
import com.android.todozen.databinding.ItemTaskListBinding
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.task.Task
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.InternalList
import com.android.todozen.core.domain.ListItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

fun adapter(
    vararg adapterDelegates: AdapterDelegate<List<ListItem>>
) = BaseAdapterDelegate(*adapterDelegates)

fun taskDelegate(
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
        binding.ivListColor.setBackgroundColor(item.list?.color ?: getColor(R.color.transparent))
        binding.tvTitle.text = item.title
        binding.tvDate.init(formatDateTime(item.date, item.time)) { text = it }
        binding.tvList.init(item.list?.id) { text = item.list?.title }
        binding.btnDone.isChecked = item.isDone
        binding.btnDone.buttonTintList = ColorStateList.valueOf(
            if (item.isDone) -7829368 else item.priority.color ?: -7829368
        )
    }
}

fun editableListDelegate(
    clickListener: (EditableList) -> Unit,
    deleteListener: ((EditableList) -> Unit)? = null
) = adapterDelegateViewBinding<EditableList, ListItem, ItemTaskListBinding>(
    { inflater, root -> ItemTaskListBinding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    binding.btnDelete.setOnClickListener { deleteListener?.invoke(item) }
    bind { payloads ->
        binding.ivColor.init(item.color, true) {
            setColorFilter(
                it,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        binding.tvTitle.text = item.title
    }
}

fun internalListDelegate(
    clickListener: (InternalList) -> Unit
) = adapterDelegateViewBinding<InternalList, ListItem, ItemTaskList2Binding>(
    { inflater, root -> ItemTaskList2Binding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    bind { payloads ->
        binding.ivColor.init(null, true) {}
        binding.tvTitle.text = item.title
    }
}


fun editableListDelegate(
    clickListener: (EditableList) -> Unit,
) = adapterDelegateViewBinding<EditableList, ListItem, ItemTaskList2Binding>(
    { inflater, root -> ItemTaskList2Binding.inflate(inflater, root, false) },
) {
    itemView.setOnClickListener { clickListener.invoke(item) }
    bind { payloads ->
        binding.ivColor.init(item.color, true) {
            setColorFilter(
                it,
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
        binding.tvTitle.text = item.title
    }
}
