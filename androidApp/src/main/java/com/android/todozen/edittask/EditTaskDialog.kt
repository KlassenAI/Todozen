package com.android.todozen.edittask

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.SharedRes
import com.android.todozen.core.BaseBottomSheetDialogFragment
import com.android.todozen.core.domain.DateTimeUtil
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.core.domain.Priority
import com.android.todozen.core.setActionDoneListener
import com.android.todozen.core.showDialog
import com.android.todozen.databinding.DialogEditTaskBinding
import com.android.todozen.editdate.EditDateDialog
import com.android.todozen.core.expect.getName
import com.android.todozen.core.expect.getString
import com.android.todozen.features.edittask.EditTaskListener
import com.android.todozen.features.edittask.EditTaskState
import com.android.todozen.features.edittask.EditTaskViewModel
import com.skydoves.powermenu.PowerMenu
import com.skydoves.powermenu.PowerMenuItem
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskDialog private constructor() :
    BaseBottomSheetDialogFragment<DialogEditTaskBinding, EditTaskState, EditTaskViewModel>(),
    EditTaskListener {

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
        viewModel.eventsDispatcher.bind(viewLifecycleOwner, this@EditTaskDialog)

        etTitle.addTextChangedListener { viewModel.updateTitle(it.toString()) }
        etTitle.setActionDoneListener {
            Log.d("bebra", state.task.toString())
            // viewModel.editTask()
        }
        btnEdit.setOnClickListener { viewModel.editTask(); dismiss() }
        containerDate.setOnClickListener {
            showDialog(EditDateDialog.getInstance(state.task.date, state.task.time))
        }
        containerList.setOnClickListener { showDialog(PickTaskListDialog.getInstance()) }
        containerMyDay.setOnClickListener { viewModel.updateInMyDay() }
        containerFavorite.setOnClickListener { viewModel.updateFavorite() }
        containerPriority.setOnClickListener { viewModel.showPriorities() }
    }

    override fun render(state: EditTaskState) = with(binding) {

        if (etTitle.text.toString() != state.task.title) {
            val wasEmpty = etTitle.text.isEmpty()
            etTitle.setText(state.task.title)
            if (wasEmpty) etTitle.setSelection(state.task.title.length)
        }

        val dateTimePair = state.dateTime
        val dateTime = formatDateTime(dateTimePair.first, dateTimePair.second)
        tvTitleDate.text = dateTime
        ivIconDate.isSelected = dateTime.orEmpty().isNotEmpty()

        ivIconMyDay.isSelected = state.task.isInMyDay

        tvTitleTaskList.text = state.list?.title ?: getString(SharedRes.strings.tasks_incoming)

        ivIconFavorite.isSelected = state.task.isFavorite

        ivIconPriority.setImageDrawable(getDrawable(state.task.priority))
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

    override fun showPriorities(priorities: List<Priority>) {
        val items = priorities.map { PowerMenuItem(title = it.type.getName(), icon = getDrawable(it)) }
        val powerMenu = PowerMenu.Builder(requireContext())
            .addItemList(items)
            .setHeight(500)
            .setMenuColor(Color.WHITE)
            .build()
        powerMenu.setOnMenuItemClickListener { position, _ ->
            viewModel.updatePriority(priorities[position])
            powerMenu.dismiss()
        }
        powerMenu.showAsAnchorRightTop(binding.containerPriority)
    }

    private fun getDrawable(priority: Priority): Drawable {
        val resId = if (priority.type.id == 4L) {
            R.drawable.ic_priority_deselected_24
        } else {
            R.drawable.ic_priority_selected_24
        }
        val drawable = ContextCompat.getDrawable(requireContext(), resId)!!
        DrawableCompat.setTint(DrawableCompat.wrap(drawable), priority.color)
        return drawable
    }
}