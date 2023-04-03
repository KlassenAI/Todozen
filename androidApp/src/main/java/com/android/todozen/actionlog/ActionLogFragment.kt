package com.android.todozen.actionlog

import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.BaseFragment
import com.android.todozen.core.StickyHeaderItemDecoration
import com.android.todozen.core.adapter
import com.android.todozen.core.domain.ListItem
import com.android.todozen.core.initVertical
import com.android.todozen.databinding.FragmentActionLogBinding
import com.android.todozen.databinding.ItemTaskLogBinding
import com.android.todozen.features.actionlog.ActionLogSpec
import com.android.todozen.features.actionlog.ActionLogViewModel
import com.android.todozen.features.actionlog.ActionLogViewModel.ActionLogState
import com.android.todozen.log.TaskLogHeaderListItem
import com.android.todozen.log.TaskLogListItem
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ActionLogFragment : BaseFragment<FragmentActionLogBinding, ActionLogState, ActionLogViewModel>() {

    override val binding by viewBinding<FragmentActionLogBinding>()
    override val viewModel by viewModel<ActionLogViewModel>()
    override val layoutId = R.layout.fragment_action_log
    private val adapter = adapter(
        taskLogHeaderListItemAdapterDelegate(),
        taskLogListItemAdapterDelegate()
    )

    @Suppress("DEPRECATION")
    override fun initViews() = with(binding) {
        val spec = requireArguments().getParcelable<ActionLogSpec>("spec")!!
        viewModel.init(spec.taskCategoryId)
        recycler.initVertical(adapter)
        val stickyDecorator = StickyHeaderItemDecoration(recycler) { adapter.items[it] is TaskLogHeaderListItem }
        recycler.addItemDecoration(stickyDecorator)
    }

    override fun initListeners() = with(binding) {
        swiper.setOnRefreshListener { viewModel.refreshData() }
    }

    override fun render(state: ActionLogState) = with(binding) {
        swiper.isRefreshing = state.isLoading
        adapter.items = state.taskLogs
    }

    private fun taskLogHeaderListItemAdapterDelegate(
    ) = adapterDelegateViewBinding<TaskLogHeaderListItem, ListItem, ItemTaskLogBinding>(
        { inflater, root -> ItemTaskLogBinding.inflate(inflater, root, false) },
    ) {
        bind { _ ->
            with(binding) {
                tvTitle.text = item.header
            }
        }
    }

    private fun taskLogListItemAdapterDelegate(
    ) = adapterDelegateViewBinding<TaskLogListItem, ListItem, ItemTaskLogBinding>(
        { inflater, root -> ItemTaskLogBinding.inflate(inflater, root, false) },
    ) {
        bind { _ ->
            with(binding) {
                tvTitle.text = item.toString()
            }
        }
    }
}