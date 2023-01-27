package com.android.todozen.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.android.todozen.R
import com.android.todozen.core.presentation.BaseState
import com.android.todozen.core.presentation.BaseViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dev.icerock.moko.mvvm.utils.bindNotNull

abstract class BaseBottomSheetDialogFragment
<VB : ViewBinding, S : BaseState, VM : BaseViewModel<S>> : BottomSheetDialogFragment() {

    abstract val binding: VB
    abstract val viewModel: VM
    abstract val layoutId: Int
    abstract var state: S

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()

        viewModel.state.bindNotNull(viewLifecycleOwner) {
            state = it
            render(it)
        }
    }

    abstract fun initViews()

    abstract fun initListeners()

    abstract fun render(state: S)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.ModalBottomSheetDialog)
    }
}