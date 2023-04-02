package com.android.todozen.core

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.android.todozen.core.presentation.BaseViewModel
import com.android.todozen.core.presentation.BaseViewModel.BaseViewModelState
import dev.icerock.moko.mvvm.utils.bindNotNull

abstract class BaseFragment<VB : ViewBinding, S : BaseViewModelState, VM : BaseViewModel<S>> :
    Fragment() {

    abstract val binding: VB
    abstract val viewModel: VM
    abstract val layoutId: Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initListeners()

        viewModel.state.bindNotNull(viewLifecycleOwner) { render(it) }
    }

    open fun initViews() {}

    open fun initListeners() {}

    abstract fun render(state: S): Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutId, container, false)
}