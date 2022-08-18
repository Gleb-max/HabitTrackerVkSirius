package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.habit.tracker.databinding.FragmentCreateRequestBinding

class CreateRequestFragment : Fragment() {

    private var _binding: FragmentCreateRequestBinding? = null
    private val binding: FragmentCreateRequestBinding
        get() = _binding ?: throw RuntimeException("FragmentCreateRequestBinding == null")

    //todo хранить во вью модели
    val photos = mutableListOf("", "", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(SelectPhotoBottomSheetFragment.REQUEST_KEY) { requestKey, bundle ->
            val photoUri = bundle.getString(SelectPhotoBottomSheetFragment.PHOTO_URI_KEY, "")
            val photoId = bundle.getInt(SelectPhotoBottomSheetFragment.PHOTO_POSITION, 1)
            if (photoUri.isNotBlank()) {
                photos[photoId] = photoUri
                //todo передалать на live data
                val v = when (photoId) {
                    0 -> binding.ivPhoto1
                    1 -> binding.ivPhoto2
                    2 -> binding.ivPhoto3
                    3 -> binding.ivPhoto4
                    else -> null
                }
                v?.let {
                    Glide.with(it.context).load(photoUri).transition(withCrossFade()).centerCrop()
                        .into(it)
                }
            }
            //todo убрать это отсюда и подписыватья на view model
        }
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

        binding.cardPhoto1.setOnClickListener {
            startSelectPhotoBottomSheet(0)
        }
        binding.cardPhoto2.setOnClickListener {
            startSelectPhotoBottomSheet(1)
        }
        binding.cardPhoto3.setOnClickListener {
            startSelectPhotoBottomSheet(2)
        }
        binding.cardPhoto4.setOnClickListener {
            startSelectPhotoBottomSheet(3)
        }
    }

    private fun startSelectPhotoBottomSheet(position: Int) {
        findNavController().navigate(
            CreateRequestFragmentDirections.actionNavigationCreateRequestToNavigationSelectPhoto(
                position
            )
        )
    }
}