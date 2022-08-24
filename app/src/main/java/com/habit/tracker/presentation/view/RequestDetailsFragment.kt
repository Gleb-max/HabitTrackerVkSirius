package com.habit.tracker.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.facebook.shimmer.ShimmerFrameLayout
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentRequestDetailsBinding
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
    private lateinit var shimmerRequest: ShimmerFrameLayout
    private lateinit var constraintRequest: ConstraintLayout
    private val component by lazy {
        (requireActivity().application as TrackerApp).component
    }

    private val args by navArgs<RequestDetailsFragmentArgs>()

    private lateinit var onRequestActionsListener: OrganizationFragment.OnRequestListActionsListener

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

        if (context is OrganizationFragment.OnRequestListActionsListener) onRequestActionsListener =
            context
        else throw RuntimeException("Activity must implement OnRequestListActionsListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        enterTransition = Slide()
        _binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shimmerRequest = binding.shimmerRequest
        shimmerRequest.startShimmer()

        constraintRequest = binding.constraintRequest

        viewModel =
            ViewModelProvider(this, viewModelFactory)[RequestDetailsViewModel::class.java]

        val like = binding.toggleLike
        val dislike = binding.toggleDislike
        val share = binding.share
        val btnBack = binding.btnBack

        observeViewModel()
        viewModel.loadRequestDetailData(args.organizationId, args.requestId)

        like.setOnClickListener {
            if (like.isChecked) dislike.isChecked = false
        }

        dislike.setOnClickListener {
            if (dislike.isChecked) like.isChecked = false
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"

            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Тема заявки: ${binding.requestName.text}\n" +
                        "Адрес организации: ${binding.address.text}\n" +
                        "Подробности: ${binding.description.text}"
            )

            startActivity(Intent.createChooser(intent, "Поделиться"))
        }
    }

    private fun observeViewModel() {
        viewModel.shimmerStopNeeded.observe(viewLifecycleOwner) {
            if (it != null) {
                shimmerRequest.stopShimmer()
                shimmerRequest.visibility = View.GONE
                constraintRequest.visibility = View.VISIBLE
            }
        }

        viewModel.request.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.requestName.text = it.title
                binding.description.text = it.description

                val photos = it.photos

                binding.carouselView.setImageListener { position, imageView ->
                    Glide.with(imageView.context).load(photos[position])
                        .transition(DrawableTransitionOptions.withCrossFade()).centerCrop()
                        .into(imageView)
                }
                binding.carouselView.pageCount = photos.size
            }
        }

        viewModel.isError.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "Не удалось загрузить страницу", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}