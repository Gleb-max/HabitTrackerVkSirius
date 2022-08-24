package com.habit.tracker.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentCreateRequestBinding
import com.habit.tracker.presentation.stateholder.CreateRequestViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import javax.inject.Inject

class CreateRequestFragment : Fragment() {

    private var _binding: FragmentCreateRequestBinding? = null
    private val binding: FragmentCreateRequestBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateRequestBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: CreateRequestViewModel
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private val args by navArgs<CreateRequestFragmentArgs>()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupOnResultListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[CreateRequestViewModel::class.java]
        observeViewModel()

        setupViews()
    }

    private fun setupViews() {
        with(binding) {
            etTitle.doOnTextChanged { text, _, _, _ ->
                text?.let { viewModel.setTitle(it.toString()) }
            }
            etDescription.doOnTextChanged { text, _, _, _ ->
                text?.let { viewModel.setDescription(it.toString()) }
            }
            btnCreate.setOnClickListener {
                binding.progressIndicatorCreateRequest.visibility = View.VISIBLE
                btnCreate.isEnabled = false
                viewModel.createRequest(args.organizationId, requireContext())
            }
        }
        setupPhotoCards()
    }

    private fun setupOnResultListener() {
        setFragmentResultListener(SelectPhotoBottomSheetFragment.REQUEST_KEY) { _, bundle ->
            val photoUri = bundle.getString(SelectPhotoBottomSheetFragment.PHOTO_URI_KEY, "")
            if (photoUri.isNotBlank()) {
                viewModel.addPhoto(photoUri)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.requestDraft.observe(viewLifecycleOwner) {
            val isAllFilled = it.name.isNotBlank() &&
                    it.description.isNotBlank() &&
                    it.photos.isNotEmpty()
            binding.btnCreate.isEnabled = isAllFilled

            with(binding) {
                val photoViews = listOf(ivPhoto1, ivPhoto2, ivPhoto3, ivPhoto4)
                for (i in 0..3) {
                    it.photos.getOrNull(i)?.let { photo ->
                        Glide.with(root.context).load(photo)
                            .transition(withCrossFade()).centerCrop().into(photoViews[i])
                    }
                }
            }
        }
    }

    private fun setupPhotoCards() {
        binding.cardPhoto1.setOnClickListener {
            startSelectPhotoBottomSheet()
        }
        binding.cardPhoto2.setOnClickListener {
            startSelectPhotoBottomSheet()
        }
        binding.cardPhoto3.setOnClickListener {
            startSelectPhotoBottomSheet()
        }
        binding.cardPhoto4.setOnClickListener {
            startSelectPhotoBottomSheet()
        }
    }

    private fun startSelectPhotoBottomSheet() {
        findNavController().navigate(R.id.navigation_select_photo)
    }
}