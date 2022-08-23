package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.data.source.local.model.AuthResult
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

        viewModel = ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
        observeViewModel()

        binding.phoneEditText.doOnTextChanged { text, _, _, _ ->
            viewModel.setPhone(text.toString())
        }
        binding.getCodeButton.setOnClickListener {
            Log.e("ferferfer", "Freferefrfrefrefr")
            viewModel.auth()
        }
    }

    private fun observeViewModel() {
        viewModel.phone.observe(viewLifecycleOwner) {
            binding.getCodeButton.isEnabled = it.matches(phoneRegex)
        }

        viewModel.authState.observe(viewLifecycleOwner) {
//            Log.e("authstate", it?.name.toString())
            val action = when (it) {
                "code" -> R.id.action_navigation_enter_phone_to_navigation_enter_code
                "reg" -> R.id.action_navigation_enter_phone_to_navigation_registration
                else -> null
            }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }
}