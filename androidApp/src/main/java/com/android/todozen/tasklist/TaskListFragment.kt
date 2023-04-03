package com.android.todozen.tasklist

import android.graphics.Color
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.SharedRes
import com.android.todozen.core.*
import com.android.todozen.core.domain.Action
import com.android.todozen.core.domain.Sort
import com.android.todozen.core.domain.Sort.*
import com.android.todozen.task.Task
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.expect.getName
import com.android.todozen.core.expect.getString
import com.android.todozen.databinding.FragmentTaskListBinding
import com.android.todozen.edittask.EditTaskDialog
import com.android.todozen.features.actionlog.ActionLogSpec
import com.android.todozen.features.edittask.EditTaskViewModel
import com.android.todozen.features.tasklist.TaskListViewModel
import com.android.todozen.features.tasklist.TaskListViewModel.EventsListener
import com.android.todozen.features.tasklist.TaskListViewModel.TaskListState
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TaskListFragment : BaseFragment<FragmentTaskListBinding, TaskListState, TaskListViewModel>(),
    EventsListener {

    override val binding by viewBinding<FragmentTaskListBinding>()
    override val viewModel by sharedViewModel<TaskListViewModel>()
    override val layoutId = R.layout.fragment_task_list

    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()

    private fun getTaskAdapter() = adapter(taskDelegate(::clickTask, ::checkTask, ::deleteTask))

    private fun clickTask(task: Task) {
        showDialog(EditTaskDialog.getInstance(task.id))
    }

    private fun checkTask(task: Task) = viewModel.checkTask(task)

    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    private var currentTasksByTitleRecycler: CustomRecyclerView? = null
    private var doneTasksRecycler: CustomRecyclerView? = null

    private var outdatedTasksRecycler: CustomRecyclerView? = null
    private var tasksRecycler: CustomRecyclerView? = null

    private var tasksByListRecyclers: List<CustomRecyclerView> = emptyList()
    private var tasksByPriorityRecyclers: List<CustomRecyclerView> = emptyList()

    override fun initViews() = with(binding) {

        (requireActivity() as AppCompatActivity).setSupportActionBar(tbContainer.toolbar)

        currentTasksByTitleRecycler =
            llInternalContainer.createRecycler(getString(SharedRes.strings.tasks_current))
        doneTasksRecycler =
            llInternalContainer.createRecycler(getString(SharedRes.strings.tasks_done))

        outdatedTasksRecycler =
            llInternalContainer.createRecycler(getString(SharedRes.strings.tasks_overdue))
        tasksRecycler =
            llInternalContainer.createRecycler(getString(SharedRes.strings.tasks_current))

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

    override fun initListeners() {
        viewModel.eventsDispatcher.bind(viewLifecycleOwner, this)
        binding.fab.setOnClickListener { viewModel.showAddTaskDialog() }
        binding.fab2.setOnClickListener { findNavController().navigate(R.id.taskList_to_menu) }
        binding.fab3.setOnClickListener { viewModel.navigateToActionLog() }
    }

    override fun render(state: TaskListState) = with(binding) {
        binding.indicator.progress = state.taskListLevel.pointsInCurrentLevel.toInt()
        binding.indicator.max = state.taskListLevel.pointsForNextLevel

        binding.tbContainer.toolbar.title = state.list?.title
        when (state.list?.sort) {
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
                tasksByListRecyclers = state.lists.map {
                    binding.llEditableContainer.createRecycler(
                        it?.title ?: getString(SharedRes.strings.tasks_incoming)
                    )
                }
                tasksByListRecyclers.indices.forEach { index ->
                    tasksByListRecyclers[index].items = state.getTasksByList(state.lists[index]?.id)
                }
            }
            PRIORITY -> {
                doneTasksRecycler!!.visible(true)

                currentTasksByTitleRecycler!!.visible(false)
                outdatedTasksRecycler!!.visible(false)
                tasksRecycler!!.visible(false)
                tasksByListRecyclers.forEach { it.visible(false) }

                tasksByPriorityRecyclers.forEach { binding.llEditableContainer.removeView(it) }
                tasksByPriorityRecyclers = state.priorities.map {
                    binding.llEditableContainer.createRecycler(it.type.getName())
                }
                tasksByPriorityRecyclers.indices.forEach { index ->
                    tasksByPriorityRecyclers[index].items =
                        state.getTasksByPriority(state.priorities[index])
                }
            }
            LABEL -> {}
            else -> {}
        }

        currentTasksByTitleRecycler!!.items = state.currentTasksByTitle
        outdatedTasksRecycler!!.items = state.outdatedTasks
        tasksRecycler!!.items = state.currentTasks
        doneTasksRecycler!!.items = state.doneTasks
    }

    private fun ViewGroup.createRecycler(title: String): CustomRecyclerView {
        return CustomRecyclerView(requireContext()).also { list ->
            list.title = title
            list.adapter = getTaskAdapter()
            list.btnHide.setOnClickListener { list.isHide = list.isHide.not() }
            addView(list)
        }
    }

    override fun showAddActionDialog(list: TaskList) {
        editTaskViewModel.updateList(list)
        showDialog(EditTaskDialog.getInstance())
    }

    override fun showMessageNextRecurringTaskCreated() {
        Toast.makeText(
            requireContext(),
            "Следующая повторяющася задача создана",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showSnackbarActionAdded(action: Action) {
        Log.d("bebra", action.toString())
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

    override fun navigateToActionLog(categoryId: Long?) {
        val bundle = bundleOf("spec" to ActionLogSpec(categoryId))
        findNavController().navigate(R.id.actionLogFragment, bundle)
    }
}