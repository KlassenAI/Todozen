package com.android.todozen.menu

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.*
import com.android.todozen.databinding.FragmentMenuBinding
import com.android.todozen.core.domain.TaskList
import com.android.todozen.edittasklist.EditTaskListDialog
import com.android.todozen.tasklist.TaskListViewModel
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding<FragmentMenuBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val listViewModel by sharedViewModel<TaskListViewModel>()
    private val adapter = adapter(listDelegate(::clickItem, ::deleteItem))
    private var state = MenuState()

    private fun clickItem(taskList: TaskList) {
        listViewModel.loadTasks(taskList.id)
        findNavController().navigate(R.id.menu_to_taskList)
    }

    private fun deleteItem(taskList: TaskList) = menuViewModel.deleteTaskList(taskList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() = with(binding) {
        rvTaskLists.initVertical(adapter) { from, to -> menuViewModel.swapTaskLists(from, to) }
        containerAllTasks.tvTitle.text = getString(R.string.all)
        containerTodayTasks.tvTitle.text = getString(R.string.my_day)
        containerIncomingTasks.tvTitle.text = getString(R.string.incoming)
        containerFavoriteTasks.tvTitle.text = getString(R.string.favorite)
        containerDeletedTasks.tvTitle.text = getString(R.string.deleted)
    }

    private fun initObservers() {
        menuViewModel.state.bindNotNull(this) {
            state = it
            adapter.items = it.taskLists
        }
    }

    private fun initListeners() = with(binding) {
        fab.setOnClickListener { showDialog(EditTaskListDialog.getInstance()) }
        containerAllTasks.container.setOnClickListener {
            listViewModel.loadAllTasks()
            findNavController().navigate(R.id.menu_to_taskList)
        }
        containerTodayTasks.container.setOnClickListener {
            listViewModel.loadTasksForToday()
            findNavController().navigate(R.id.menu_to_taskList)
        }
        containerIncomingTasks.container.setOnClickListener {
            listViewModel.loadTasks(null)
            findNavController().navigate(R.id.menu_to_taskList)
        }
        containerFavoriteTasks.container.setOnClickListener {
            listViewModel.loadFavoriteTasks()
            findNavController().navigate(R.id.menu_to_taskList)
        }
        containerDeletedTasks.container.setOnClickListener {
            listViewModel.loadDeletedTasks()
            findNavController().navigate(R.id.menu_to_taskList)
        }
    }
}