package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.habit.tracker.R
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

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthViewModel::class.java]
        observeViewModel()

        setupCodeField()
        binding.changeNumber.setOnClickListener {
            viewModel.clearFields()
            findNavController().navigate(R.id.action_navigation_enter_code_to_navigation_enter_phone)
        }
    }

    private fun observeViewModel() {
        viewModel.phone.observe(viewLifecycleOwner) {
            binding.phoneNumber.text = it
        }
        viewModel.authState.observe(viewLifecycleOwner) {
            if (it == "logged_in") findNavController().navigate(R.id.action_navigation_main)
        }
    }

    private fun setupCodeField() {
        val editTextNumber = binding.editTextNumber
        val editTextNumber2 = binding.editTextNumber2
        val editTextNumber3 = binding.editTextNumber3
        val editTextNumber4 = binding.editTextNumber4

        editTextNumber.addTextChangedListener(GenericTextWatcher(editTextNumber, editTextNumber2))
        editTextNumber2.addTextChangedListener(GenericTextWatcher(editTextNumber2, editTextNumber3))
        editTextNumber3.addTextChangedListener(GenericTextWatcher(editTextNumber3, editTextNumber4))
        editTextNumber4.addTextChangedListener(
            GenericTextWatcher(
                editTextNumber4,
                null,
                this::onCodeReady
            )
        )

        editTextNumber.setOnKeyListener(GenericKeyEvent(editTextNumber, null))
        editTextNumber2.setOnKeyListener(GenericKeyEvent(editTextNumber2, editTextNumber))
        editTextNumber3.setOnKeyListener(GenericKeyEvent(editTextNumber3, editTextNumber2))
        editTextNumber4.setOnKeyListener(GenericKeyEvent(editTextNumber4, editTextNumber3))

//        editTextNumber.isFocusable = false
//        editTextNumber2.isFocusable = false
//        editTextNumber3.isFocusable = false
//        editTextNumber4.isFocusable = false
    }

    private fun onCodeReady() {
        //todo красиво собирать код через view model
        val code = binding.editTextNumber.text.toString() +
                binding.editTextNumber2.text.toString() +
                binding.editTextNumber3.text.toString() +
                binding.editTextNumber4.text.toString()
        viewModel.setCode(code)
        viewModel.login()
    }
}