package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.FragmentEnterCodeBinding

class EnterCodeFragment : Fragment() {

    private var _binding: FragmentEnterCodeBinding? = null
    private val binding: FragmentEnterCodeBinding
        get() = _binding ?: throw RuntimeException("FragmentEnterCodeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }
}