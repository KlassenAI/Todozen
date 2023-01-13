package com.android.todozen.features.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.android.todozen.R
import com.android.todozen.databinding.FragmentMenuBinding

class MenuFragment : Fragment(R.layout.fragment_menu) {

    private val binding by viewBinding<FragmentMenuBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.menu_to_taskList)
        }
    }
}