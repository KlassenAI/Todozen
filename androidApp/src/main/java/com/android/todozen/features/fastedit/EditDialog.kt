package com.android.todozen.features.fastedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogEditBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class EditDialog private constructor(): BottomSheetDialogFragment() {

    private val binding by viewBinding<DialogEditBinding>()
    private val viewModel by sharedViewModel<TaskViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getLongArray("taskId")?.let { longArray ->
            longArray.firstOrNull().let { viewModel.loadTask(it) }
        }

        viewModel.state.bind(viewLifecycleOwner) { state ->
            state?.let {
                binding.etTitle.setText(it.title)
            }
        }

        binding.btnEdit.setOnClickListener {
            updateEdits()
            viewModel.editTask()
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

    companion object {
        fun getInstance(
            taskId: Long? = null
        ): EditDialog {
            return EditDialog().apply {
                taskId?.let {
                    arguments = bundleOf("taskId" to longArrayOf(taskId))
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit, container, false)
}