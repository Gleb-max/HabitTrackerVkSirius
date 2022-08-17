package com.habit.tracker

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.imageview.ShapeableImageView
import java.io.File
import java.util.jar.Manifest

class CreateRequestFragment: Fragment(R.layout.request) {

    lateinit var openBottomSheet: ShapeableImageView
    lateinit var openBottomSheet2: ShapeableImageView
    lateinit var openBottomSheet3: ShapeableImageView
    lateinit var openBottomSheet4: ShapeableImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        openBottomSheet = view.findViewById<ShapeableImageView>(R.id.SViewLoadGallery)
        openBottomSheet2 = view.findViewById<ShapeableImageView>(R.id.SViewLoadGallery2)
        openBottomSheet3 = view.findViewById<ShapeableImageView>(R.id.SViewLoadGallery3)
        openBottomSheet4 = view.findViewById<ShapeableImageView>(R.id.SViewLoadGallery4)


        bottomSheetListener(openBottomSheet)
        bottomSheetListener(openBottomSheet2)
        bottomSheetListener(openBottomSheet3)
        bottomSheetListener(openBottomSheet4)
    }

    private fun bottomSheetListener(pic: ShapeableImageView) {
        pic.setOnClickListener {
            val view: View = layoutInflater.inflate(R.layout.bottom_sheet_ar, null)
            val dialog = BottomSheetDialog(requireContext())
            dialog.setContentView(view)
            dialog.show()

            val photoChoice = view.findViewById<CardView>(R.id.photo_choice)
            val arChoice = view.findViewById<CardView>(R.id.ar_choice)
            val galleryChoice = view.findViewById<CardView>(R.id.gallery_choice)

            photoChoice.setOnClickListener {
                val takeImageIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                // TODO: запросить пермишны
                if (takeImageIntent.resolveActivity(requireContext().packageManager) != null) {
                    startActivityForResult(takeImageIntent, 200)
                }

            }

            arChoice.setOnClickListener {
                // здесь будет AR
                Toast.makeText(context, "taking photo ar", Toast.LENGTH_SHORT).show()
            }

            galleryChoice.setOnClickListener {
                Toast.makeText(context, "choosing photo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == Activity.RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as? Bitmap
                openBottomSheet.setImageBitmap(imageBitmap)
            }
        }
    }
}