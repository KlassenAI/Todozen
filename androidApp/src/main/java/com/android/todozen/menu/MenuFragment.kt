package com.android.todozen.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.*
import com.android.todozen.databinding.FragmentMenuBinding
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.domain.TaskList
import com.android.todozen.edittasklist.EditTaskListDialog
import com.android.todozen.tasklist.TaskListViewModel
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding<FragmentMenuBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val listViewModel by sharedViewModel<TaskListViewModel>()
    private val editableListsAdapter = adapter(editableListDelegate(::clickItem, ::deleteItem))
    private val internalListsAdapter = adapter(internalListDelegate(::clickItem))
    private var state = MenuState()

    private fun clickItem(list: TaskList) {
        listViewModel.getListTasks(list)
        findNavController().navigate(R.id.menu_to_taskList)
    }

    private fun deleteItem(taskList: EditableList) = menuViewModel.deleteTaskList(taskList)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() = with(binding) {
        rvInternalLists.initVertical(internalListsAdapter)
        rvEditableLists.initVertical(editableListsAdapter) { from, to -> menuViewModel.swapTaskLists(from, to) }
    }

    private fun initObservers() {
        menuViewModel.state.bindNotNull(this) {
            state = it
            internalListsAdapter.items = it.internalLists
            editableListsAdapter.items = it.editableLists
        }
    }

    private fun initListeners() = with(binding) {
        fab.setOnClickListener { showDialog(EditTaskListDialog.getInstance()) }
    }
}