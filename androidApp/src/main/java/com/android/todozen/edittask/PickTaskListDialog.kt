package com.android.todozen.edittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogPickTaskListBinding
import com.android.todozen.core.domain.EditableList
import com.android.todozen.core.BaseAdapterDelegate
import com.android.todozen.core.adapter
import com.android.todozen.core.initVertical
import com.android.todozen.core.editableListDelegate
import com.android.todozen.features.edittask.EditTaskViewModel
import com.android.todozen.features.menu.MenuState
import com.android.todozen.features.menu.MenuViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PickTaskListDialog private constructor() : BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogPickTaskListBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()
    private var state = MenuState()
    private var adapter: BaseAdapterDelegate? = null

    private fun getAdapter() = adapter(editableListDelegate(::clickItem))

    private fun clickItem(taskList: EditableList) {
        editTaskViewModel.updateTaskList(taskList)
        dismiss()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
    }

    private fun initViews() {
        adapter = getAdapter()
        binding.recycler.initVertical(adapter!!)
    }

    private fun initObservers() {
        menuViewModel.state.bindNotNull(viewLifecycleOwner) {
            this.state = it
            adapter?.items = it.editableLists
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }

    companion object {
        fun getInstance() = PickTaskListDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_pick_task_list, container, false)
}