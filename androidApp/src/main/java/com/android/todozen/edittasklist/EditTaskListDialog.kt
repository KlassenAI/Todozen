package com.android.todozen.edittasklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogEditTaskListBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskListDialog private constructor(): BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditTaskListBinding>()
    private val viewModel by sharedViewModel<EditTaskListViewModel>()
    private var state = EditTaskListState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        val taskListId = arguments?.getLongArray(TASK_LIST_ID)?.firstOrNull()
        viewModel.loadTaskList(taskListId)
    }

    private fun initObservers() {
        viewModel.state.bindNotNull(viewLifecycleOwner) {
            this.state = it
            binding.etTitle.setText(it.title)
        }
    }

    private fun initListeners() {
        binding.btnEdit.setOnClickListener {
            viewModel.editTaskList()
            dismiss()
        }
        binding.etTitle.addTextChangedListener {
            viewModel.updateTitle(it.toString())
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
        ) = EditTaskListDialog().apply {
            val bundle = bundleOf()
            taskListId?.let { bundle.putLongArray(TASK_LIST_ID, longArrayOf(taskListId)) }
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task_list, container, false)
}