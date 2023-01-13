package com.android.todozen.features.edittask

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogEditTaskBinding
import com.android.todozen.utils.DateTimeUtil.formatDateTime
import com.android.todozen.features.editdate.EditDateDialog
import com.android.todozen.features.core.str
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskDialog private constructor(): BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditTaskBinding>()
    private val viewModel by sharedViewModel<EditTaskViewModel>()
    private var state = EditTaskState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLongArray("taskId")?.let { longArray ->
            longArray.firstOrNull().let { viewModel.loadTask(it) }
        }

        viewModel.state.bind(viewLifecycleOwner) { state -> state?.let { render(state) } }

        initListeners()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }

    private fun render(state: EditTaskState) {
        this.state = state
        binding.etTitle.str = state.title
        binding.tvTitleDate.text = formatDateTime(state.date, state.time)
    }

    private fun initListeners() {
        binding.btnEdit.setOnClickListener {
            updateEdits()
            viewModel.editTask()
            dismiss()
        }
        binding.containerDate.setOnClickListener {
            updateEdits()
            EditDateDialog.getInstance(state.date, state.time)
                .show(childFragmentManager, EditDateDialog::class.simpleName)
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
        fun getInstance(taskId: Long? = null) = EditTaskDialog().apply {
            taskId?.let {
                arguments = bundleOf("taskId" to longArrayOf(taskId))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task, container, false)
}