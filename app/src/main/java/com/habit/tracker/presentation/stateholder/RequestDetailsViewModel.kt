package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.usecase.GetOrganizationUseCase
import com.habit.tracker.domain.usecase.GetRequestUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class RequestDetailsViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase
) : BaseViewModel() {

    private val _request = MutableLiveData<Request?>(null)
    val request: LiveData<Request?> = _request

    private val _organization = MutableLiveData<Organization?>(null)
    val organization: LiveData<Organization?> = _organization

    private val _shimmerStopNeeded = MutableLiveData<Boolean?>(null)
    val shimmerStopNeeded: LiveData<Boolean?> = _shimmerStopNeeded

    private val _isError = MutableLiveData<Boolean?>(null)
    val isError: LiveData<Boolean?> = _isError

    fun loadRequestDetailData(organizationId: Int, requestId: Int) {
        viewModelScope.execute (
                onSuccess = {
                viewModelScope.launch {
                    _shimmerStopNeeded.value = true
                }
            }, onError = {
                viewModelScope.launch {
                    _isError.value = true
                }
            }, function = {
                _request.value = getRequestUseCase(organizationId, requestId)
                _organization.value = getOrganizationUseCase(organizationId)
            }
        )
    }
}