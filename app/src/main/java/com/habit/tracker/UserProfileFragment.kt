package com.habit.tracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.UserProfileBinding

class UserProfileFragment: Fragment() {

    private lateinit var binding: UserProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = UserProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}