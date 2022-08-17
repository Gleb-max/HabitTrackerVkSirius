package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentOrganizationBottomSheetBinding
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.presentation.stateholder.OrganizationBottomSheetViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import com.habit.tracker.presentation.view.adapter.RequestListAdapter
import javax.inject.Inject

class OrganizationBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentOrganizationBottomSheetBinding? = null
    private val binding: FragmentOrganizationBottomSheetBinding
        get() = _binding ?: throw RuntimeException("FragmentTaskListBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: OrganizationBottomSheetViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private var organizationId: Int = ORGANIZATION_UNDEFINED_ID

    private lateinit var requestListAdapter: RequestListAdapter

    private lateinit var onRequestListActionsListener: OnRequestListActionsListener

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

        if (context is OnRequestListActionsListener) onRequestListActionsListener = context
        else throw RuntimeException("Activity must implement OnRequestListActionsListener")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(ORGANIZATION_ID)) {
            throw RuntimeException("Param organization id is absent")
        }
        organizationId = args.getInt(ORGANIZATION_ID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrganizationBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[OrganizationBottomSheetViewModel::class.java]

        setupRecyclerView()
        observeViewModel()
        viewModel.loadOrganizationData(organizationId)

        binding.fabAddRequest.setOnClickListener {
            onRequestListActionsListener.onAddNewClick()
            dismissAllowingStateLoss()
        }
    }

    private fun setupRecyclerView() {
        requestListAdapter = RequestListAdapter().apply {
            onRequestItemClickListener = { request ->
                onRequestListActionsListener.onRequestItemClick(request)
                dismissAllowingStateLoss()
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

    companion object {

        private const val ORGANIZATION_ID = "organization_id"
        private const val ORGANIZATION_UNDEFINED_ID = -1

        fun newInstance(organizationId: Int): OrganizationBottomSheetFragment {
            return OrganizationBottomSheetFragment().apply {
                arguments = bundleOf(
                    ORGANIZATION_ID to organizationId,
                )
            }
        }
    }
}