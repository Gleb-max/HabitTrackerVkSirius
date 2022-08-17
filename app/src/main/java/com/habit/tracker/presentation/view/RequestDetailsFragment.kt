package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.WathBinding

class RequestDetailsFragment : Fragment() {

    private var _binding: WathBinding? = null
    private val binding: WathBinding
        get() = _binding ?: throw RuntimeException("WathBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WathBinding.inflate(inflater, container, false)
        return binding.root
    }
}