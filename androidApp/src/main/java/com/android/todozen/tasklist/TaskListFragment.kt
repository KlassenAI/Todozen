package com.android.todozen.tasklist

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.SharedRes
import com.android.todozen.core.*
import com.android.todozen.core.domain.Sort
import com.android.todozen.core.domain.Sort.*
import com.android.todozen.core.domain.Task
import com.android.todozen.databinding.FragmentTaskListBinding
import com.android.todozen.edittask.EditTaskDialog
import com.android.todozen.edittasklist.EditTaskListListener
import com.android.todozen.expect.getName
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import dev.icerock.moko.mvvm.utils.bindNotNull
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : Fragment(R.layout.fragment_task_list), EditTaskListListener {

    private val binding by viewBinding<FragmentTaskListBinding>()
    private val viewModel by sharedViewModel<TaskListViewModel>()
    private var state = TaskListState()

    private fun getTaskAdapter() = adapter(taskDelegate(::clickTask, ::checkTask, ::deleteTask))

    private fun clickTask(task: Task) = showDialog(EditTaskDialog.getInstance(task.id))
    private fun checkTask(task: Task) = viewModel.checkTask(task)
    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    private var currentTasksByTitleRecycler: CustomRecyclerView? = null
    private var doneTasksRecycler: CustomRecyclerView? = null

    private var outdatedTasksRecycler: CustomRecyclerView? = null
    private var tasksRecycler: CustomRecyclerView? = null

    private var tasksByListRecyclers: List<CustomRecyclerView> = emptyList()
    private var tasksByPriorityRecyclers: List<CustomRecyclerView> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
//        val str = StringDesc.Resource(SharedRes.strings.test).toString(requireContext())
//        Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
    }

    private fun initViews() = with(binding) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.tbContainer.toolbar)

        currentTasksByTitleRecycler = llInternalContainer.createRecycler(getString(R.string.tasks_current))
        doneTasksRecycler = llInternalContainer.createRecycler(getString(R.string.tasks_done))

        outdatedTasksRecycler = llInternalContainer.createRecycler(getString(R.string.tasks_overdue))
        tasksRecycler = llInternalContainer.createRecycler(getString(R.string.tasks_current))

        requireActivity().addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.tasks_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
                R.id.sort -> {
                    viewModel.showSorts(); true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initObservers() {
        viewModel.eventsDispatcher.bind(viewLifecycleOwner, this)
        viewModel.state.bindNotNull(this) {

            binding.tbContainer.toolbar.title = it.list?.title
            when (it.list?.sort) {
                TITLE -> {
                    currentTasksByTitleRecycler!!.visible(true)
                    doneTasksRecycler!!.visible(true)

                    outdatedTasksRecycler!!.visible(false)
                    tasksRecycler!!.visible(false)
                    tasksByListRecyclers.forEach { it.visible(false) }
                    tasksByPriorityRecyclers.forEach { it.visible(false) }
                }
                DATE -> {
                    outdatedTasksRecycler!!.visible(true)
                    tasksRecycler!!.visible(true)
                    doneTasksRecycler!!.visible(true)

                    currentTasksByTitleRecycler!!.visible(false)
                    tasksByListRecyclers.forEach { it.visible(false) }
                    tasksByPriorityRecyclers.forEach { it.visible(false) }
                }
                LIST -> {
                    doneTasksRecycler!!.visible(true)

                    outdatedTasksRecycler!!.visible(false)
                    tasksRecycler!!.visible(false)
                    currentTasksByTitleRecycler!!.visible(false)
                    tasksByPriorityRecyclers.forEach { it.visible(false) }

                    tasksByListRecyclers.forEach { binding.llEditableContainer.removeView(it) }
                    tasksByListRecyclers = it.lists.map {
                        binding.llEditableContainer.createRecycler(
                            it.title.ifEmpty { getString(R.string.tasks_incoming) }
                        )
                    }
                    tasksByListRecyclers.indices.forEach { index ->
                        tasksByListRecyclers[index].items = it.getTasksByList(it.lists[index].id)
                    }
                }
                PRIORITY -> {
                    doneTasksRecycler!!.visible(true)

                    currentTasksByTitleRecycler!!.visible(false)
                    outdatedTasksRecycler!!.visible(false)
                    tasksRecycler!!.visible(false)
                    tasksByListRecyclers.forEach { it.visible(false) }

                    tasksByPriorityRecyclers.forEach { binding.llEditableContainer.removeView(it) }
                    tasksByPriorityRecyclers = it.priorities.map {
                        binding.llEditableContainer.createRecycler(it.type.getName())
                    }
                    tasksByPriorityRecyclers.indices.forEach { index ->
                        tasksByPriorityRecyclers[index].items = it.getTasksByPriority(it.priorities[index])
                    }
                }
                LABEL -> {}
                else -> {}
            }

            currentTasksByTitleRecycler!!.items = it.currentTasksByTitle
            outdatedTasksRecycler!!.items = it.outdatedTasks
            tasksRecycler!!.items = it.currentTasks
            doneTasksRecycler!!.items = it.doneTasks
            state = it
        }
    }

    private fun initListeners() {
        binding.fab.setOnClickListener { showDialog(EditTaskDialog.getInstance()) }
        binding.fab2.setOnClickListener { findNavController().navigate(R.id.taskList_to_menu) }
    }

    private fun ViewGroup.createRecycler(title: String): CustomRecyclerView {
        return CustomRecyclerView(requireContext()).also { list ->
            list.title = title
            list.adapter = getTaskAdapter()
            list.btnHide.setOnClickListener { list.isHide = list.isHide.not() }
            addView(list)
        }
    }

    override fun showSorts(sorts: List<Sort>) {
        val items = sorts.map { PowerMenuItem(title = it.name) }
        val powerMenu = PowerMenu.Builder(requireContext())
            .addItemList(items)
            .setHeight(500)
            .setMenuColor(Color.WHITE)
            .build()
        powerMenu.setOnMenuItemClickListener { position, _ ->
            viewModel.updateSort(sorts[position])
            powerMenu.dismiss()
        }
        powerMenu.showAsAnchorRightBottom(binding.tbContainer.toolbar)
    }
}