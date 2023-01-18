package com.android.todozen.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.FragmentMenuBinding
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.BaseAdapterDelegate
import com.android.todozen.core.initVertical
import com.android.todozen.core.showDialog
import com.android.todozen.core.taskListsAdapterDelegate
import com.android.todozen.edittasklist.EditTaskListDialog
import com.android.todozen.tasklist.TaskListViewModel
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding<FragmentMenuBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val taskListViewModel by sharedViewModel<TaskListViewModel>()
    private val adapter = BaseAdapterDelegate(
        taskListsAdapterDelegate(::clickItem, ::deleteItem)
    )

    private fun clickItem(taskList: TaskList) {
        taskListViewModel.loadTasks(taskList.id)
        findNavController().navigate(R.id.menu_to_taskList)
    }

    private fun deleteItem(taskList: TaskList) {
        menuViewModel.deleteTaskList(taskList)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        binding.rvTaskLists.initVertical(adapter)
        binding.containerAllTasks.tvTitle.text = getString(R.string.all)
        binding.containerTodayTasks.tvTitle.text = getString(R.string.my_day)
        binding.containerIncomingTasks.tvTitle.text = getString(R.string.incoming)
    }

    private fun initObservers() {
        menuViewModel.state.bindNotNull(this) {
            adapter.items = it.taskLists
        }
    }

    private fun initListeners() {
        binding.fab.setOnClickListener {
            showDialog(EditTaskListDialog.getInstance())
        }
        binding.containerAllTasks.container.setOnClickListener {
            taskListViewModel.loadAllTasks()
            findNavController().navigate(R.id.menu_to_taskList)
        }
        binding.containerTodayTasks.container.setOnClickListener {
            taskListViewModel.loadTasksForToday()
            findNavController().navigate(R.id.menu_to_taskList)
        }
        binding.containerIncomingTasks.container.setOnClickListener {
            taskListViewModel.loadTasks(null)
            findNavController().navigate(R.id.menu_to_taskList)
        }
    }
}