package com.android.todozen.edittask

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.BaseBottomSheetDialogFragment
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.core.setActionDoneListener
import com.android.todozen.core.showDialog
import com.android.todozen.databinding.DialogEditTaskBinding
import com.android.todozen.editdate.EditDateDialog
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskDialog private constructor() :
    BaseBottomSheetDialogFragment<DialogEditTaskBinding, EditTaskState, EditTaskViewModel>() {

    override val binding by viewBinding<DialogEditTaskBinding>()
    override val viewModel by sharedViewModel<EditTaskViewModel>()
    override val layoutId: Int = R.layout.dialog_edit_task
    override var state = EditTaskState()

    override fun initViews() {
        val taskId = arguments?.getLongArray(TASK_ID)?.firstOrNull()
        viewModel.loadTask(taskId)
        binding.etTitle.requestFocus()
    }

    override fun initListeners() = with(binding) {
        etTitle.addTextChangedListener { viewModel.updateTitle(it.toString()) }
        etTitle.setActionDoneListener { viewModel.editTask(state) }
        btnEdit.setOnClickListener { viewModel.editTask(state); dismiss() }
        containerDate.setOnClickListener {
            showDialog(EditDateDialog.getInstance(state.task.date, state.task.time))
        }
        containerList.setOnClickListener {
            showDialog(PickTaskListDialog.getInstance(state.task.listId))
        }
        containerMyDay.setOnClickListener { viewModel.updateInMyDay() }
        containerFavorite.setOnClickListener { viewModel.updateFavorite() }
    }

    override fun render(state: EditTaskState) = with(binding) {
        if (etTitle.text.toString() != state.task.title) {
            val wasEmpty = etTitle.text.isEmpty()
            etTitle.setText(state.task.title)
            if (wasEmpty) etTitle.setSelection(state.task.title.length)
        }
        val dateTime = formatDateTime(state.task.date, state.task.time)
        tvTitleDate.text = dateTime
        ivIconDate.isSelected = dateTime.orEmpty().isNotEmpty()
        val listTitle = state.task.listTitle
        tvTitleTaskList.text = listTitle
        ivIconTaskList.isSelected = listTitle.isNotEmpty()
        ivIconMyDay.isSelected = state.task.isInMyDay
        ivIconFavorite.isSelected = state.task.isFavorite
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
}