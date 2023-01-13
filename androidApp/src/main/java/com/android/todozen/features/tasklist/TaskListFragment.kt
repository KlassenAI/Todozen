package com.android.todozen.features.tasklist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.FragmentTaskListBinding
import com.android.todozen.domain.Task
import com.android.todozen.features.edittask.EditTaskDialog
import com.android.todozen.features.core.BaseAdapterDelegate
import com.android.todozen.features.core.initVertical
import com.android.todozen.features.core.tasksAdapterDelegate
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list) {

    private val binding by viewBinding<FragmentTaskListBinding>()
    private val viewModel by sharedViewModel<TaskListViewModel>()

    private val tasksAdapter = getTaskAdapter()
    private val doneTasksAdapter = getTaskAdapter()

    private fun getTaskAdapter() = BaseAdapterDelegate(
        tasksAdapterDelegate(::clickTask, ::checkTask, ::deleteTask)
    )

    private fun clickTask(task: Task) {
        EditTaskDialog.getInstance(task.id).show(childFragmentManager, EditTaskDialog::class.simpleName)
    }
    private fun checkTask(task: Task) = viewModel.checkTask(task)
    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvTasks.initVertical(tasksAdapter)
        binding.rvDoneTasks.initVertical(doneTasksAdapter)

        createRecycler(tasksAdapter)
        createRecycler(doneTasksAdapter)

        binding.fab.setOnClickListener {
            EditTaskDialog.getInstance().show(childFragmentManager, EditTaskDialog::class.simpleName)
        }

        binding.fab2.setOnClickListener { findNavController().navigate(R.id.taskList_to_menu) }

        viewModel.state.bind(this) { state ->
            state?.let {
                tasksAdapter.items = state.tasks
                doneTasksAdapter.items = state.doneTasks
            }
        }
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