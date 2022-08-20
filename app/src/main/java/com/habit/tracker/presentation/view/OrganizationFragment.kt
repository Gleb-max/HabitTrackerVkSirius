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
import com.habit.tracker.databinding.FragmentOrganizationBinding
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.presentation.stateholder.OrganizationBottomSheetViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import com.habit.tracker.presentation.view.adapter.RequestListAdapter
import javax.inject.Inject

class OrganizationFragment : Fragment() {

    private var _binding: FragmentOrganizationBinding? = null
    private val binding: FragmentOrganizationBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: OrganizationBottomSheetViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private lateinit var requestListAdapter: RequestListAdapter

    private lateinit var onRequestListActionsListener: OnRequestListActionsListener

    private val args by navArgs<OrganizationFragmentArgs>()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

        if (context is OnRequestListActionsListener) onRequestListActionsListener = context
        else throw RuntimeException("Activity must implement OnRequestListActionsListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganizationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[OrganizationBottomSheetViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        viewModel.loadOrganizationData(args.organizationId)

        binding.fabAddRequest.setOnClickListener {
            onRequestListActionsListener.onAddNewClick()
        }
    }

    private fun setupRecyclerView() {
        requestListAdapter = RequestListAdapter().apply {
            onRequestItemClickListener = { request ->
                onRequestListActionsListener.onRequestItemClick(request)
            }
        }
        with(binding.rvRequestList) {
            adapter = requestListAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.organization.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.tvStatements.text = it.name
            }
        }

        viewModel.requests.observe(viewLifecycleOwner) {
            requestListAdapter.submitList(it)
        }
    }

    interface OnRequestListActionsListener {

        fun onRequestItemClick(request: Request)

        fun onAddNewClick()
    }
}