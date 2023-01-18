package com.android.todozen.edittask

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.core.showDialog
import com.android.todozen.databinding.DialogEditTaskBinding
import com.android.todozen.editdate.EditDateDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bindNotNull
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskDialog private constructor() : BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditTaskBinding>()
    private val viewModel by sharedViewModel<EditTaskViewModel>()
    private var state = EditTaskState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        initListeners()
    }

    private fun initViews() {
        val taskId = arguments?.getLongArray(TASK_ID)?.firstOrNull()
        viewModel.loadTask(taskId)
        binding.etTitle.requestFocus()
    }

    private fun initObservers() {
        viewModel.state.bindNotNull(viewLifecycleOwner) {
            state = it
            if (binding.etTitle.text.toString() != it.title) {
                val wasEmpty = binding.etTitle.text.isEmpty()
                binding.etTitle.setText(it.title)
                if (wasEmpty) binding.etTitle.setSelection(it.title.length)
            }
            binding.tvTitleDate.text = formatDateTime(it.date, it.time)
            binding.tvTitleTaskList.text = it.listTitle
            binding.ivIconMyDay.imageTintList = ContextCompat.getColorStateList(
                requireContext(), if (it.inMyDay) R.color.black else R.color.gray
            )
        }
    }

    private fun initListeners() {
        binding.etTitle.addTextChangedListener {
            viewModel.updateTitle(it.toString())
        }
        binding.etTitle.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.editTask()
                handled = true
            }
            handled
        }
        binding.btnEdit.setOnClickListener {
            viewModel.editTask()
            dismiss()
        }
        binding.containerDate.setOnClickListener {
            showDialog(EditDateDialog.getInstance(state.date, state.time))
        }
        binding.containerList.setOnClickListener {
            showDialog(PickTaskListDialog.getInstance(state.listId))
        }
        binding.containerMyDay.setOnClickListener {
            viewModel.updateInMyDay()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }

    companion object {

        const val TASK_ID = "taskId"

        fun getInstance(
            taskId: Long? = null
        ) = EditTaskDialog().apply {
            val bundle = bundleOf()
            taskId?.let { bundle.putLongArray(TASK_ID, longArrayOf(taskId)) }
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task, container, false)
}