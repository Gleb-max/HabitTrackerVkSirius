package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButton
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentEnterPhoneBinding
import com.habit.tracker.presentation.stateholder.AuthViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class EnterPhoneFragment : Fragment() {

    private var _binding: FragmentEnterPhoneBinding? = null
    private val binding: FragmentEnterPhoneBinding
        get() = _binding ?: throw RuntimeException("FragmentEnterPhoneBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: AuthViewModel
    private lateinit var codeButton: MaterialButton
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private val phoneRegex = "^[+]?[0-9]{10,13}\$".toRegex()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEnterPhoneBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val progressBar = binding.progressIndicatorEnterPhone
        codeButton = binding.getCodeButton

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory)[AuthViewModel::class.java]
        observeViewModel()

        binding.phoneEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setPhone(text.toString())
        }
        codeButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            codeButton.isEnabled = false
            viewModel.auth()
        }
    }

    private fun observeViewModel() {
        viewModel.phone.observe(viewLifecycleOwner) {
            codeButton.isEnabled = it.matches(phoneRegex)
        }
        viewModel.authState.observe(viewLifecycleOwner) {
            val action = when (it) {
                "code" -> R.id.action_navigation_enter_phone_to_navigation_enter_code
                "reg" -> R.id.action_navigation_enter_phone_to_navigation_registration
                else -> null
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
        viewModel.isError.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(
                    context, getString(R.string.create_request_success),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}