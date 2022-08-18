package com.habit.tracker.presentation.view

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.habit.tracker.databinding.FragmentSelectPhotoBottomSheetBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SelectPhotoBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentSelectPhotoBottomSheetBinding? = null
    private val binding: FragmentSelectPhotoBottomSheetBinding
        get() = _binding ?: throw RuntimeException("FragmentSelectPhotoBottomSheetBinding == null")

    //todo во viewmodel
    private var url = ""

    private val args by navArgs<SelectPhotoBottomSheetFragmentArgs>()

    private val getPhotoGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            setFragmentResult(
                REQUEST_KEY, bundleOf(
                    PHOTO_URI_KEY to uri.toString(),
                    PHOTO_POSITION to args.photoPosition
                )
            )
            dismiss()
        }

    private val getPhotoCamera =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { ok: Boolean ->
            setFragmentResult(
                REQUEST_KEY, bundleOf(
                    PHOTO_URI_KEY to if (ok) url else "",
                    PHOTO_POSITION to args.photoPosition
                )
            )
            dismiss()
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSelectPhotoBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.cardAr.setOnClickListener {
            //todo
        }
        binding.cardPhoto.setOnClickListener {
            startTakePicture()
        }
        binding.cardGallery.setOnClickListener {
            getPhotoGallery.launch("image/*")
        }
    }

    private fun startTakePicture() {
        val file = try {
            createImageFile()
        } catch (exc: Exception) {
            null
        }
        if (file == null) Toast.makeText(
            requireContext(),
            "Не удалось открыть камеру", Toast.LENGTH_SHORT
        ).show()
        else {
            val uri =
                FileProvider.getUriForFile(
                    requireContext(),
                    "${requireActivity().packageName}.fileprovider",
                    file
                )
            url = uri.toString()
            getPhotoCamera.launch(uri)
        }
    }

    //todo вынести логику с фрагмента
    private fun createImageFile(): File? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    companion object {

        const val REQUEST_KEY = "photo_request"
        const val PHOTO_URI_KEY = "photo_uri"
        const val PHOTO_POSITION = "photo_position"
    }
}