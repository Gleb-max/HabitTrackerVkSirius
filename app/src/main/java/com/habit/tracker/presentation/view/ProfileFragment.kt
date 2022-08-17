package com.habit.tracker.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.habit.tracker.CreateRequestFragment
import com.habit.tracker.R
import com.habit.tracker.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding: FragmentProfileBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.make.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_profile, CreateRequestFragment())
            transaction?.disallowAddToBackStack()
            transaction?.commit()
        }
    // это BottomSheet, надо прикрутить это к карте
        // и кнопку тоже переопределить
//        val buttonSheet = binding.buttonSheet
//        buttonSheet.setOnClickListener {
//
//            // отображаем BottomSheet
//            val view: View = layoutInflater.inflate(R.layout.item_bottom_sheet, null)
//            val dialog = BottomSheetDialog(requireContext())
//            dialog.setContentView(view)
//            dialog.show()
//
//            // связываем SeekBar с его TextView
//            val seekBar = view.findViewById<SeekBar>(R.id.seek_bar_filter)
//            val seekNum = view.findViewById<TextView>(R.id.seek_num)
//
//            var startPoint = 0
//            var endPoint = 100
//
//            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
//                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
//                    seekNum.text = p1.toString()
//                }
//
//                override fun onStartTrackingTouch(p0: SeekBar?) {
//                    if (seekBar != null) {
//                        startPoint = seekBar.progress
//                    }
//                }
//
//                override fun onStopTrackingTouch(p0: SeekBar?) {
//                    if (seekBar != null) {
//                        endPoint = seekBar.progress
//                    }
//                }
//
//            })
//        }
    }
}