package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : BaseViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean?>(null)
    val isLoggedIn: LiveData<Boolean?> = _isLoggedIn

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _phone = MutableLiveData<String>()
    val phone: LiveData<String> = _phone

    private val _name = MutableLiveData<String>()
    val name: LiveData<String> = _name

    private val _code = MutableLiveData<String>()
    val code: LiveData<String> = _code

    private val _authState = MutableLiveData<String?>(null)
    val authState: LiveData<String?> = _authState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.isLoggedIn()
            _isLoggedIn.postValue(result)
        }
    }

    fun setPhone(value: String) {
        _phone.value = value
    }

    fun setName(value: String) {
        _name.value = value
    }

    fun setCode(value: String) {
        _code.value = value
    }

    fun clearFields() {
        _authState.value = ""
        _isError.value = true
    }

    fun auth() {
        viewModelScope.execute(
            onError = {
                _isError.value = true
            }
        ) {
            val phone = phone.value
            _authState.postValue(repository.auth(phone!!))
        }
    }

    fun reg() {
        viewModelScope.execute(
            onError = {
                _isError.value = true
            }
        ) {
            val phone = phone.value
            val name = name.value
            _authState.postValue(repository.reg(phone!!, name!!))
        }
    }

    fun login() {
        viewModelScope.execute(
            onError = {
                _isError.value = true
            }
        ) {
            val phone = phone.value
            val code = code.value
            val token = repository.login(phone!!, code!!)
            if (token != null) _authState.postValue("logged_in")
            else _isError.postValue(true)
        }
    }
}