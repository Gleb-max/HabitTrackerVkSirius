package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.usecase.GetRequestUseCase
import javax.inject.Inject

class RequestDetailsViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase,
) : BaseViewModel() {

    private val _request = MutableLiveData<Request?>(null)
    val request: LiveData<Request?> = _request

    private val _shimmerStopNeeded = MutableLiveData<Boolean?>(null)
    val shimmerStopNeeded: LiveData<Boolean?> = _shimmerStopNeeded

    private val _isError = MutableLiveData<Boolean?>(null)
    val isError: LiveData<Boolean?> = _isError

    fun loadRequestDetailData(organizationId: Int, requestId: Int) {
        viewModelScope.execute(
            onSuccess = {
                _shimmerStopNeeded.value = true
            }, onError = {
                _isError.value = true
            }) {
            _request.postValue(getRequestUseCase(organizationId, requestId))
        }
    }
}