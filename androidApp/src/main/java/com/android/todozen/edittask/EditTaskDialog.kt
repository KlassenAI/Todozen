package com.android.todozen.edittask

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogEditTaskBinding
import com.android.todozen.core.showDialog
import com.android.todozen.core.domain.DateTimeUtil.formatDateTime
import com.android.todozen.core.str
import com.android.todozen.editdate.EditDateDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditTaskDialog private constructor(): BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditTaskBinding>()
    private val viewModel by sharedViewModel<EditTaskViewModel>()
    private var state = EditTaskState()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskId = arguments?.getLongArray(TASK_ID)?.firstOrNull()
        val listId = arguments?.getLongArray(LIST_ID)?.firstOrNull()
        Log.d("aboba", "$taskId $listId")
        viewModel.loadTask(taskId, listId)



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
        binding.tvTitleTaskList.text = state.listTitle
    }

    private fun initListeners() {
        binding.btnEdit.setOnClickListener {
            updateEdits()
            viewModel.editTask()
            dismiss()
        }
        binding.containerDate.setOnClickListener {
            updateEdits()
            showDialog(EditDateDialog.getInstance(state.date, state.time))
        }
        binding.containerList.setOnClickListener {
            updateEdits()
            showDialog(PickTaskListDialog.getInstance(state.listId))
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

        const val TASK_ID = "taskId"
        const val LIST_ID = "listId"

        fun getInstance(
            taskId: Long? = null,
            listId: Long? = null
        ) = EditTaskDialog().apply {
            val bundle = bundleOf()
            taskId?.let { bundle.putLongArray(TASK_ID, longArrayOf(taskId)) }
            listId?.let { bundle.putLongArray(LIST_ID, longArrayOf(listId)) }
            arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task, container, false)
}