package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentRegistrationBinding
import com.habit.tracker.presentation.stateholder.AuthViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationBinding == null")

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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        )[AuthViewModel::class.java]
        observeViewModel()

        binding.fullNameEditText.doOnTextChanged { text, _, _, _ ->
            text?.let { viewModel.setName(it.toString()) }
        }
        binding.tvChangeNumber.setOnClickListener {
            viewModel.clearFields()
            findNavController().navigate(R.id.action_navigation_registration_to_navigation_enter_phone)
        }
        binding.btnRegister.setOnClickListener {
            viewModel.reg()
        }
    }

    private fun observeViewModel() {
        viewModel.phone.observe(viewLifecycleOwner) {
            binding.tvPhone.text = getString(R.string.title_with_phone, it)
        }
        viewModel.name.observe(viewLifecycleOwner) {
            binding.btnRegister.isEnabled = it.isNotBlank()
        }
        viewModel.authState.observe(viewLifecycleOwner) {
            when (it) {
                "code" -> findNavController().navigate(R.id.action_navigation_registration_to_navigation_enter_code)
            }
        }
    }
}