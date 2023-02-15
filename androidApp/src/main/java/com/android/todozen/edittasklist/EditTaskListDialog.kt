package com.android.todozen.edittasklist

import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.core.BaseBottomSheetDialogFragment
import com.android.todozen.core.getColor
import com.android.todozen.core.setActionDoneListener
import com.android.todozen.databinding.DialogEditTaskListBinding
import com.android.todozen.menu.MenuViewModel
import com.flask.colorpicker.ColorPickerView.WHEEL_TYPE
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskListDialog private constructor() :
    BaseBottomSheetDialogFragment<DialogEditTaskListBinding, EditTaskListState, EditTaskListViewModel>() {

    override val binding by viewBinding<DialogEditTaskListBinding>()
    override val viewModel by sharedViewModel<EditTaskListViewModel>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    override val layoutId = R.layout.dialog_edit_task_list
    override var state = EditTaskListState()

    override fun initViews() {
        val taskListId = arguments?.getLongArray(TASK_LIST_ID)?.firstOrNull()
        viewModel.loadTaskList(taskListId)
        binding.etTitle.requestFocus()
    }

    override fun initListeners() = with(binding) {
        etTitle.addTextChangedListener { viewModel.updateTitle(it.toString()) }
        etTitle.setActionDoneListener {
            viewModel.editTaskList();
            menuViewModel.loadEditableLists()
        }
        containerFavorite.setOnClickListener { viewModel.updateFavorite() }
        containerColor.setOnClickListener {
            val colorPickerBuilder = ColorPickerDialogBuilder
                .with(context)
                .setTitle(getString(R.string.select_color))
                .wheelType(WHEEL_TYPE.CIRCLE)
                .density(12)
                .setPositiveButton("ok") { _, color, _ -> viewModel.updateColor(color) }
            state.taskList.color?.let { colorPickerBuilder.initialColor(it) }
            colorPickerBuilder.build().show()
        }
        containerColor.setOnLongClickListener { viewModel.clearColor(); true }
        btnEdit.setOnClickListener {
            viewModel.editTaskList()
            menuViewModel.loadEditableLists()
            dismiss()
        }
    }

    override fun render(state: EditTaskListState): Unit = with(binding) {
        if (etTitle.text.toString() != state.taskList.title) {
            val wasEmpty = etTitle.text.isEmpty()
            etTitle.setText(state.taskList.title)
            if (wasEmpty) etTitle.setSelection(state.taskList.title.length)
        }
        val color = state.taskList.color ?: getColor(R.color.gray)
        ivIconFavorite.isSelected = state.taskList.isFavorite
        ivIconColor.isSelected = state.taskList.color != null
        ivIconColor.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
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
}