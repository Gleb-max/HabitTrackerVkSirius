package com.habit.tracker.presentation.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.facebook.shimmer.ShimmerFrameLayout
import com.habit.tracker.R
import com.habit.tracker.TrackerApp
import com.habit.tracker.databinding.FragmentRequestDetailsBinding
import com.habit.tracker.presentation.stateholder.RequestDetailsViewModel
import com.habit.tracker.presentation.stateholder.ViewModelFactory
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
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
        val carousel = binding.carouselView
        val share = binding.share
        val btnBack = binding.btnBack

        // todo: подставить картинки с бэка
        val sampleImages = intArrayOf(
            R.drawable.maxresdefault,
            R.drawable.maxresdefault,
            R.drawable.maxresdefault,
            R.drawable.maxresdefault
        )

        carousel.setImageListener { position, imageView ->
            imageView.setImageResource(sampleImages[position])
        }
        carousel.pageCount = sampleImages.size

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
                "Организация: ${binding.organizationName.text}\n" +
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

        viewModel.organization.observe(viewLifecycleOwner) {
            if (it != null) binding.organizationName.text = it.name
        }

        viewModel.request.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.requestName.text = it.title
                binding.description.text = it.description
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