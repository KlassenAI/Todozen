package com.android.todozen.edittask

import android.os.Bundle
import android.util.Log
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
import com.android.todozen.core.getColorList
import com.android.todozen.core.setImage
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
            if (binding.etTitle.text.toString() != it.task.title) {
                val wasEmpty = binding.etTitle.text.isEmpty()
                binding.etTitle.setText(it.task.title)
                if (wasEmpty) binding.etTitle.setSelection(it.task.title.length)
            }
            val dateTime = formatDateTime(it.task.date, it.task.time)
            binding.tvTitleDate.text = dateTime
            binding.ivIconDate.isSelected = dateTime.orEmpty().isNotEmpty()
            val listTitle = it.task.listTitle
            binding.tvTitleTaskList.text = listTitle
            binding.ivIconTaskList.isSelected = listTitle.isNotEmpty()
            binding.ivIconMyDay.isSelected = it.task.isInMyDay
            binding.ivIconFavorite.isSelected = it.task.isFavorite
        }
    }

    private fun initListeners() = with(binding) {
        etTitle.addTextChangedListener { viewModel.updateTitle(it.toString()) }
        etTitle.setOnEditorActionListener { _, i, _ ->
            var handled = false
            if (i == EditorInfo.IME_ACTION_DONE) {
                viewModel.editTask(state)
                handled = true
            }
            handled
        }
        btnEdit.setOnClickListener {
            viewModel.editTask(state)
            dismiss()
        }
        containerDate.setOnClickListener {
            showDialog(EditDateDialog.getInstance(state.task.date, state.task.time))
        }
        containerList.setOnClickListener {
            showDialog(PickTaskListDialog.getInstance(state.task.listId))
        }
        containerMyDay.setOnClickListener { viewModel.updateInMyDay() }
        containerFavorite.setOnClickListener { viewModel.updateFavorite() }
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