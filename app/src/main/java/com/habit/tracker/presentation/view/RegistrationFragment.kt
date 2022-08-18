package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding: FragmentRegistrationBinding
        get() = _binding ?: throw RuntimeException("FragmentRegistrationBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val register = binding.btnRegister
        val name = binding.fullNameEditText
        val nickname = binding.nickNameEditText

        binding.btnRegister.setOnClickListener {
            if (name.length() == 0) {
                name.error = "Необходимы имя и фамилия"
            }
            if (nickname.length() == 0) {
                nickname.error = "Необходимо имя пользователя"
            }
            // TODO: заменить проверку на существующее имя пользователя
            if (register == null) {
                nickname.error = "Пользователь с таким именем уже существует"
            }
        }
    }
}