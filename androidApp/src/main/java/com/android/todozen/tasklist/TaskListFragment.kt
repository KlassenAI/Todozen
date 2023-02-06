package com.android.todozen.tasklist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.*
import com.android.todozen.core.domain.ListItem
import com.android.todozen.core.domain.Task
import com.android.todozen.databinding.FragmentTaskListBinding
import com.android.todozen.edittask.EditTaskDialog
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by viewBinding<FragmentTaskListBinding>()
    private val viewModel by sharedViewModel<TaskListViewModel>()

    private fun getTaskAdapter() = adapter(taskDelegate(::clickTask, ::checkTask, ::deleteTask))

    private fun clickTask(task: Task) = showDialog(EditTaskDialog.getInstance(task.id))
    private fun checkTask(task: Task) = viewModel.checkTask(task)
    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    private var outdatedTasksRecycler: CustomRecyclerView? = null
    private var tasksRecycler: CustomRecyclerView? = null
    private var doneTasksRecycler: CustomRecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        outdatedTasksRecycler = createRecycler("Просроченные")
        tasksRecycler = createRecycler("Текущие")
        doneTasksRecycler = createRecycler("Выполненные")
    }

    private fun initObservers() {
        viewModel.state.bindNotNull(this) {
            outdatedTasksRecycler!!.items = it.outdatedTasks
            tasksRecycler!!.items = it.currentTasks
            doneTasksRecycler!!.items = it.doneTasks
        }
    }

    private fun initListeners() {
        binding.fab.setOnClickListener { showDialog(EditTaskDialog.getInstance()) }
        binding.fab2.setOnClickListener { findNavController().navigate(R.id.taskList_to_menu) }
    }

    private fun createRecycler(title: String) = CustomRecyclerView(requireContext()).also { list ->
        list.title = title
        list.adapter = getTaskAdapter()
        list.btnHide.setOnClickListener { list.isHide = list.isHide.not() }
        binding.llContainer.addView(list)
    }
}