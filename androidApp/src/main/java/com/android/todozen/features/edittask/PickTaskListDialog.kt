package com.android.todozen.features.edittask

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.DialogPickTaskListBinding
import com.android.todozen.domain.TaskList
import com.android.todozen.features.core.BaseAdapterDelegate
import com.android.todozen.features.core.taskListsAdapterDelegate
import com.android.todozen.features.menu.MenuViewModel
import dev.icerock.moko.mvvm.utils.bind
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PickTaskListDialog private constructor() : DialogFragment() {

    private val binding by viewBinding<DialogPickTaskListBinding>()
    private val menuViewModel by sharedViewModel<MenuViewModel>()
    private val editTaskViewModel by sharedViewModel<EditTaskViewModel>()
    private val adapter = BaseAdapterDelegate(
        taskListsAdapterDelegate(::clickItem)
    )

    private fun clickItem(taskList: TaskList) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getLongArray("taskId")?.let { longArray ->
            longArray.firstOrNull().let { menuViewModel.loadTask(it) }
        }

        menuViewModel.state.bind(viewLifecycleOwner) { state -> state?.let { render(state) } }

        initListeners()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        menuViewModel.clearState()
    }

    companion object {

        const val

        fun getInstance(taskListId: Long? = null) = PickTaskListDialog().apply {
            taskListId?.let {
                arguments = bundleOf("taskId" to longArrayOf(taskListId))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.dialog_edit_task, container, false)
}