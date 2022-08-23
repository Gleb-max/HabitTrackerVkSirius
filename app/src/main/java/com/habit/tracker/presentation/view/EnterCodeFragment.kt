package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentEnterCodeBinding
import com.habit.tracker.presentation.generics.GenericKeyEvent
import com.habit.tracker.presentation.generics.GenericTextWatcher
import com.habit.tracker.presentation.stateholder.AuthViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class EnterCodeFragment : Fragment() {

    private var _binding: FragmentEnterCodeBinding? = null
    private val binding: FragmentEnterCodeBinding
        get() = _binding ?: throw RuntimeException("FragmentEnterCodeBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AuthViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterCodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity(), viewModelFactory)[AuthViewModel::class.java]

        setupCodeField()
    }

    private fun setupCodeField() {
        val editTextNumber = binding.editTextNumber
        val editTextNumber2 = binding.editTextNumber2
        val editTextNumber3 = binding.editTextNumber3
        val editTextNumber4 = binding.editTextNumber4

        editTextNumber.addTextChangedListener(GenericTextWatcher(editTextNumber, editTextNumber2))
        editTextNumber2.addTextChangedListener(GenericTextWatcher(editTextNumber2, editTextNumber3))
        editTextNumber3.addTextChangedListener(GenericTextWatcher(editTextNumber3, editTextNumber4))
        editTextNumber4.addTextChangedListener(GenericTextWatcher(editTextNumber4, null, this::onCodeReady))

        editTextNumber.setOnKeyListener(GenericKeyEvent(editTextNumber, null))
        editTextNumber2.setOnKeyListener(GenericKeyEvent(editTextNumber2, editTextNumber))
        editTextNumber3.setOnKeyListener(GenericKeyEvent(editTextNumber3, editTextNumber2))
        editTextNumber4.setOnKeyListener(GenericKeyEvent(editTextNumber4, editTextNumber3))
    }

    private fun onCodeReady() {
        Log.e("frrffrfre", binding.editTextNumber.text.toString() + binding.editTextNumber2.text.toString() + binding.editTextNumber3.text.toString() + binding.editTextNumber4.text.toString())

    }
}