package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentRequestDetailsBinding
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.presentation.stateholder.OrganizationBottomSheetViewModel
import com.habit.tracker.presentation.stateholder.RequestDetailsViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class RequestDetailsFragment : Fragment() {

    private var _binding: FragmentRequestDetailsBinding? = null
    private val binding: FragmentRequestDetailsBinding
        get() = _binding ?: throw RuntimeException("FragmentRequestDetailsBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: RequestDetailsViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private val args by navArgs<RequestDetailsFragmentArgs>()

    private lateinit var onRequestActionsListener: OrganizationFragment.OnRequestListActionsListener

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

        if (context is OrganizationFragment.OnRequestListActionsListener) onRequestActionsListener = context
        else throw RuntimeException("Activity must implement OnRequestListActionsListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[RequestDetailsViewModel::class.java]

        val like = binding.toggleLike
        val dislike = binding.toggleDislike

        observeViewModel()
        viewModel.loadRequestDetailData(args.organizationId, args.requestId)

        like.setOnClickListener {
            if (like.isChecked) dislike.isChecked = false
        }
        dislike.setOnClickListener {
            if (dislike.isChecked) like.isChecked = false
        }
    }

    private fun observeViewModel() {
        viewModel.organization.observe(viewLifecycleOwner) {
            if (it != null) binding.organizationName.text = it.name
        }

        viewModel.request.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.requestName.text = it.title
                binding.description.text = it.description
            }
        }
    }
}