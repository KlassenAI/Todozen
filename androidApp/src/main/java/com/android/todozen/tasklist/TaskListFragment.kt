package com.android.todozen.tasklist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.*
import com.android.todozen.databinding.FragmentTaskListBinding
import com.android.todozen.core.domain.Task
import com.android.todozen.edittask.EditTaskDialog
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by viewBinding<FragmentTaskListBinding>()
    private val viewModel by sharedViewModel<TaskListViewModel>()

    private val tasksAdapter = getTaskAdapter()
    private val doneTasksAdapter = getTaskAdapter()

    private fun getTaskAdapter() = adapter(taskDelegate(::clickTask, ::checkTask, ::deleteTask))

    private fun clickTask(task: Task) = showDialog(EditTaskDialog.getInstance(task.id))
    private fun checkTask(task: Task) = viewModel.checkTask(task)
    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        binding.rvTasks.initVertical(tasksAdapter)
        binding.rvDoneTasks.initVertical(doneTasksAdapter)

        createRecycler(tasksAdapter)
        createRecycler(doneTasksAdapter)
    }

    private fun initObservers() {
        viewModel.state.bindNotNull(this) {
            tasksAdapter.items = it.tasks
            doneTasksAdapter.items = it.doneTasks
        }
    }

    private fun initListeners() {
        binding.fab.setOnClickListener { showDialog(EditTaskDialog.getInstance()) }
        binding.fab2.setOnClickListener { findNavController().navigate(R.id.taskList_to_menu) }
    }

    @SuppressLint("InflateParams")
    private fun createRecycler(adapter: BaseAdapterDelegate) {
        val recycler =
            LayoutInflater.from(requireContext()).inflate(R.layout.layout_tasks, null, false) as RecyclerView
        recycler.initVertical(adapter)
        binding.llContainer.addView(recycler)
    }

    private fun clearRecyclers() {
        binding.llContainer.removeAllViews()
    }
}