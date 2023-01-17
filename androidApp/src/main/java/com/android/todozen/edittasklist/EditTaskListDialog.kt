package com.android.todozen.edittasklist

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogEditTaskListBinding
import com.android.todozen.core.str
import com.android.todozen.edittasklist.EditTaskListState
import com.android.todozen.edittasklist.EditTaskListViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskListDialog private constructor(): BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditTaskListBinding>()
    private val viewModel by sharedViewModel<EditTaskListViewModel>()
    private var state = EditTaskListState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLongArray(TASK_LIST_ID)?.let { longArray ->
            longArray.firstOrNull().let { viewModel.loadTaskList(it) }
        }

        viewModel.state.bind(viewLifecycleOwner) { state -> state?.let { render(state) } }

        initListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }

    private fun render(state: EditTaskListState) {
        this.state = state
        binding.etTitle.str = state.title
    }

    private fun initListeners() {
        binding.btnEdit.setOnClickListener {
            updateEdits()
            viewModel.editTaskList()
            dismiss()
        }
    }

    override fun onPause() {
        super.onPause()
        updateEdits()
    }

    private fun updateEdits() {
        viewModel.updateTitle(binding.etTitle.text.toString())
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        viewModel.clearState()
    }

    companion object {

        const val TASK_LIST_ID = "taskListId"

        fun getInstance(taskListId: Long? = null) = EditTaskListDialog().apply {
            taskListId?.let {
                arguments = bundleOf(TASK_LIST_ID to longArrayOf(taskListId))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task_list, container, false)
}