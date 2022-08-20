package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.habit.tracker.databinding.FragmentRequestDetailsBinding

class RequestDetailsFragment : Fragment() {

    private var _binding: FragmentRequestDetailsBinding? = null
    private val binding: FragmentRequestDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentRequestDetailsBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val like = binding.toggleLike
        val dislike = binding.toggleDislike

        like.setOnClickListener {
            if (like.isChecked) dislike.isChecked = false
        }
        dislike.setOnClickListener {
            if (dislike.isChecked) like.isChecked = false
        }
    }
}