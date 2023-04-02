package com.android.todozen.actionlog

import android.util.Log
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.BaseFragment
import com.android.todozen.core.adapter
import com.android.todozen.core.domain.ListItem
import com.android.todozen.core.initVertical
import com.android.todozen.databinding.FragmentActionLogBinding
import com.android.todozen.databinding.ItemTaskLogBinding
import com.android.todozen.features.actionlog.ActionLogSpec
import com.android.todozen.features.actionlog.ActionLogViewModel
import com.android.todozen.features.actionlog.ActionLogViewModel.ActionLogState
import com.android.todozen.features.actionlog.TaskLog
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class ActionLogFragment : BaseFragment<FragmentActionLogBinding, ActionLogState, ActionLogViewModel>() {

    override val binding by viewBinding<FragmentActionLogBinding>()
    override val viewModel by viewModel<ActionLogViewModel>()
    override val layoutId = R.layout.fragment_action_log
    private val adapter = adapter(taskLogAdapterDelegate())

    override fun initViews() = with(binding) {
        Log.d("aboba", "initViews")
        val spec = requireArguments().getParcelable<ActionLogSpec>("spec")!!
        viewModel.init(spec.taskCategoryId)
        recycler.initVertical(adapter)
    }

    override fun initListeners() = with(binding) {
        swiper.setOnRefreshListener { viewModel.refreshData() }
    }

    override fun render(state: ActionLogState) = with(binding) {
        Log.d("aboba", state.toString())
        swiper.isRefreshing = state.isLoading
        adapter.items = state.taskLogs
    }

    private fun taskLogAdapterDelegate(
    ) = adapterDelegateViewBinding<TaskLog, ListItem, ItemTaskLogBinding>(
        { inflater, root -> ItemTaskLogBinding.inflate(inflater, root, false) },
    ) {
        bind { _ ->
            with(binding) {
                tvTitle.text = item.toString()
            }
        }
    }
}