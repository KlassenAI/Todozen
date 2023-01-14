package com.android.todozen.features.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.FragmentMenuBinding
import com.android.todozen.domain.TaskList
import com.android.todozen.features.core.BaseAdapterDelegate
import com.android.todozen.features.core.initVertical
import com.android.todozen.features.core.taskListsAdapterDelegate
import com.android.todozen.features.edittasklist.EditTaskListDialog
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding<FragmentMenuBinding>()
    private val viewModel by sharedViewModel<MenuViewModel>()
    private val adapter = BaseAdapterDelegate(
        taskListsAdapterDelegate(::clickItem, ::deleteItem)
    )

    private fun clickItem(taskList: TaskList) {
        EditTaskListDialog.getInstance(taskList.id).show(childFragmentManager, EditTaskListDialog::class.simpleName)
    }

    private fun deleteItem(taskList: TaskList) {
        viewModel.deleteTaskList(taskList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        binding.rvTaskLists.initVertical(adapter)
    }

    private fun initObservers() {
        viewModel.state.bind(this) { state ->
            state?.let { adapter.items = state.taskLists }
        }
    }

    private fun initListeners() {
        binding.fab.setOnClickListener {
            EditTaskListDialog.getInstance().show(childFragmentManager, EditTaskListDialog::class.simpleName)
        }
    }
}