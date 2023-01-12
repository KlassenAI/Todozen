package com.android.todozen.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.android.todozen.domain.ListItem
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class BaseAdapterDelegate(
    vararg adapterDelegates: AdapterDelegate<List<ListItem>>
) : AsyncListDifferDelegationAdapter<ListItem>(object : DiffUtil.ItemCallback<ListItem>() {

    override fun areItemsTheSame(old: ListItem, new: ListItem): Boolean = old.getUuid() == new.getUuid()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(old: ListItem, new: ListItem): Boolean = old == new

}) {

    init {
        adapterDelegates.forEach { delegatesManager.addDelegate(it) }
    }

    fun setItems(items: List<ListItem>, commitCallback: (() -> Unit)?) {
        differ.submitList(items, commitCallback)
    }
}

