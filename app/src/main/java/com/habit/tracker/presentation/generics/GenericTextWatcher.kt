package com.habit.tracker.presentation.generics

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.habit.tracker.R

class GenericTextWatcher internal constructor(private val currentView: View, private val nextView: View?) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) { // TODO Auto-generated method stub
        val text = editable.toString()
        when (currentView.id) {
            R.id.editTextNumber -> if (text.isNotEmpty()) nextView!!.requestFocus()
            R.id.editTextNumber2 -> if (text.isNotEmpty()) nextView!!.requestFocus()
            R.id.editTextNumber3 -> if (text.isNotEmpty()) nextView!!.requestFocus()
            R.id.editTextNumber4 -> if (text.isNotEmpty()) {
                val imm = currentView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(currentView.windowToken, 0)
            }
            //You can use EditText4 same as above to hide the keyboard
        }
    }

    override fun beforeTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) {
    }

    override fun onTextChanged(
        arg0: CharSequence,
        arg1: Int,
        arg2: Int,
        arg3: Int
    ) { // Auto-generated method stub
    }

}