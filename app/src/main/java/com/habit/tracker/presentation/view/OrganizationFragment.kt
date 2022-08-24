package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.facebook.shimmer.ShimmerFrameLayout
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentOrganizationBinding
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.presentation.stateholder.OrganizationViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import com.habit.tracker.presentation.view.adapter.RequestListAdapter
import javax.inject.Inject

class OrganizationFragment : Fragment() {

    private var _binding: FragmentOrganizationBinding? = null
    private val binding: FragmentOrganizationBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: OrganizationViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private lateinit var requestListAdapter: RequestListAdapter

    private lateinit var onRequestListActionsListener: OnRequestListActionsListener

    private lateinit var shimmerOrganization: ShimmerFrameLayout

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
            ViewModelProvider(this, viewModelFactory)[OrganizationViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        viewModel.loadOrganizationData(args.organizationId)

        shimmerOrganization = binding.shimmerOrganization
        shimmerOrganization.startShimmer()

        binding.fabAddRequest.setOnClickListener {
            onRequestListActionsListener.onAddNewClick(args.organizationId)
        }

        binding.btnRetry.setOnClickListener {
            observeViewModel()
            viewModel.loadOrganizationData(args.organizationId)
        }
    }

    private fun setupRecyclerView() {
        requestListAdapter = RequestListAdapter().apply {
            onRequestItemClickListener = { request ->
                onRequestListActionsListener.onRequestItemClick(args.organizationId, request)
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
            if (it != null) {
                requestListAdapter.submitList(it)
            }
        }

        viewModel.shimmerCloseNeeded.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerOrganization.stopShimmer()
                shimmerOrganization.visibility = View.GONE
                viewState(false)
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it != null) {
                viewState(it)
            }
        }
    }

    private fun viewState(state: Boolean) {
        binding.tvStatements.isVisible = !state
        binding.rvRequestList.isVisible = !state

        binding.tvNoInternet.isVisible = state
        binding.ivNoInternetPlaceholder.isVisible = state
        binding.btnRetry.isVisible = state
    }

    interface OnRequestListActionsListener {

        fun onRequestItemClick(organizationId: Int, request: Request)

        fun onAddNewClick(organizationId: Int)
    }
}