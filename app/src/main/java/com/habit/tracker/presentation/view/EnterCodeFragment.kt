package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.FragmentEnterCodeBinding
import com.habit.tracker.presentation.generics.GenericKeyEvent
import com.habit.tracker.presentation.generics.GenericTextWatcher

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editTextNumber = binding.editTextNumber
        val editTextNumber2 = binding.editTextNumber2
        val editTextNumber3 = binding.editTextNumber3
        val editTextNumber4 = binding.editTextNumber4

        editTextNumber.addTextChangedListener(GenericTextWatcher(editTextNumber, editTextNumber2))
        editTextNumber2.addTextChangedListener(GenericTextWatcher(editTextNumber2, editTextNumber3))
        editTextNumber3.addTextChangedListener(GenericTextWatcher(editTextNumber3, editTextNumber4))
        editTextNumber4.addTextChangedListener(GenericTextWatcher(editTextNumber4, null))

        editTextNumber.setOnKeyListener(GenericKeyEvent(editTextNumber, null))
        editTextNumber2.setOnKeyListener(GenericKeyEvent(editTextNumber2, editTextNumber))
        editTextNumber3.setOnKeyListener(GenericKeyEvent(editTextNumber3, editTextNumber2))
        editTextNumber4.setOnKeyListener(GenericKeyEvent(editTextNumber4,editTextNumber3))
    }
}