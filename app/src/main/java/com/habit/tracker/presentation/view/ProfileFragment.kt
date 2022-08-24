package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentProfileBinding
import com.habit.tracker.presentation.stateholder.ProfileViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProfileViewModel
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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(
            this, viewModelFactory
        )[ProfileViewModel::class.java]
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.profile.observe(viewLifecycleOwner) {
            if (it != null) {
                with(binding) {
                    tvName.text = it.name
                    Glide.with(root.context)
                        .load(it.image)
                        .transition(DrawableTransitionOptions.withCrossFade()).centerCrop()
                        .into(binding.ivAvatar)
                }
            }
        }
    }
}