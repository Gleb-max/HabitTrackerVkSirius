package com.habit.tracker.presentation.stateholder

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.data.repository.AuthRepository
import com.habit.tracker.di.ApplicationScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean?>(null)
    val isLoggedIn: LiveData<Boolean?> = _isLoggedIn

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _authState = MutableLiveData<String?>(null)
    val authState: LiveData<String?> = _authState

    var a = 111

    init {

        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isLoggedIn()
            withContext(Dispatchers.Main) {
                _isLoggedIn.value = result
            }
        }
    }

    fun setPhone(value: String) {
        _phone.value = value
    }

    fun auth() {
        viewModelScope.execute {
            val phone = phone.value
            a = 222
            _authState.value = repository.auth(phone!!)
        }
    }
}