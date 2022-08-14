package com.habit.tracker.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.FragmentTeamListBinding

class TeamListFragment : Fragment() {

    private var _binding: FragmentTeamListBinding? = null
    private val binding: FragmentTeamListBinding
        get() = _binding ?: throw RuntimeException("FragmentTeamListBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTeamListBinding.inflate(inflater, container, false)
        return binding.root
    }
}