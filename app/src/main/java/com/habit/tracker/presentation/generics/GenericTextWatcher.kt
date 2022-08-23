package com.habit.tracker.presentation.generics

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.habit.tracker.R

class GenericTextWatcher internal constructor(
    private val currentView: View,
    private val nextView: View?,
    private val cb: (() -> Unit)? = null
) :
    TextWatcher {
    override fun afterTextChanged(editable: Editable) {
        val text = editable.toString()
        when (currentView.id) {
            R.id.editTextNumber -> if (text.isNotEmpty()) nextView?.requestFocus()
            R.id.editTextNumber2 -> if (text.isNotEmpty()) nextView?.requestFocus()
            R.id.editTextNumber3 -> if (text.isNotEmpty()) nextView?.requestFocus()
            R.id.editTextNumber4 -> if (text.isNotEmpty()) {
                val imm =
                    currentView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(currentView.windowToken, 0)
                Log.e("gtrgtgr", "vrgrgrrgt")
                cb?.invoke()
            }
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
    ) {
    }

}