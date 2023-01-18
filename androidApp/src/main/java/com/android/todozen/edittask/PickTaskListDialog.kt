package com.android.todozen.edittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogPickTaskListBinding
import com.android.todozen.core.domain.TaskList
import com.android.todozen.core.BaseAdapterDelegate
import com.android.todozen.core.initVertical
import com.android.todozen.core.taskListsAdapterDelegate
import com.android.todozen.menu.MenuState
import com.android.todozen.menu.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PickTaskListDialog private constructor() : BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogPickTaskListBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()
    private var state = MenuState()
    private var adapter: BaseAdapterDelegate? = null

    private fun getAdapter(currentTaskListId: Long?) = BaseAdapterDelegate(
        taskListsAdapterDelegate(currentTaskListId, ::clickItem)
    )

    private fun clickItem(taskList: TaskList) {
        editTaskViewModel.updateTaskList(taskList.id, taskList.title)
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        val taskListId = arguments?.getLongArray(TASK_LIST_ID)?.firstOrNull()
        adapter = getAdapter(taskListId)
        binding.recycler.initVertical(adapter!!)
    }

    private fun initObservers() {
        menuViewModel.state.bindNotNull(viewLifecycleOwner) {
            this.state = it
            adapter?.items = it.taskLists
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }

    companion object {

        const val TASK_LIST_ID = "taskListId"

        fun getInstance(
            taskListId: Long? = null
        ) = PickTaskListDialog().apply {
            val bundle = bundleOf()
            taskListId?.let { bundle.putLongArray(TASK_LIST_ID, longArrayOf(taskListId)) }
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_pick_task_list, container, false)
}