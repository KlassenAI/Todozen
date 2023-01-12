package com.android.todozen.features.tasklist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.todozen.databinding.ActivityMainBinding
import com.android.todozen.features.fastedit.EditDialog
import com.android.todozen.domain.Task
import com.android.todozen.utils.*
import dev.icerock.moko.mvvm.utils.bind
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val viewModel: TaskListViewModel by inject()

    private val tasksAdapter = BaseAdapterDelegate(
        tasksAdapterDelegate(::clickTask, ::checkTask, ::deleteTask)
    )

    private val doneTasksAdapter =  BaseAdapterDelegate(
        tasksAdapterDelegate(::clickTask, ::checkTask, ::deleteTask)
    )

    private fun clickTask(task: Task) {
        EditDialog.getInstance(task.id).show(supportFragmentManager, EditDialog::class.simpleName)
    }

    private fun checkTask(task: Task) = viewModel.checkTask(task)
    private fun deleteTask(task: Task) = viewModel.deleteTask(task)

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.initVertical(tasksAdapter)
        binding.rvDoneTasks.initVertical(doneTasksAdapter)

        binding.fab.setOnClickListener {
            EditDialog.getInstance().show(supportFragmentManager, EditDialog::class.simpleName)
        }

        viewModel.state.bind(this) { state ->
            state?.let {
                tasksAdapter.items = state.tasks
                doneTasksAdapter.items = state.doneTasks
            }
        }
    }

    private fun handleEffect(effect: TaskListSideEffect) {
        when (effect) {
            is TaskListSideEffect.Toast -> toast(effect.message)
            is TaskListSideEffect.Error -> TODO()
            TaskListSideEffect.Success -> TODO()
        }
    }
}
