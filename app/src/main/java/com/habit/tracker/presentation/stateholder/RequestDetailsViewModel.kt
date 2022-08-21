package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.usecase.GetOrganizationUseCase
import com.habit.tracker.domain.usecase.GetRequestUseCase
import javax.inject.Inject

class RequestDetailsViewModel @Inject constructor(
    private val getRequestUseCase: GetRequestUseCase,
    private val getOrganizationUseCase: GetOrganizationUseCase
) : BaseViewModel() {

    private val _request = MutableLiveData<Request?>(null)
    val request: LiveData<Request?> = _request

    private val _organization = MutableLiveData<Organization?>(null)
    val organization: LiveData<Organization?> = _organization

    fun loadRequestDetailData(organizationId: Int, requestId: Int) {
        viewModelScope.execute {
            _request.value = getRequestUseCase(organizationId, requestId)
            _organization.value = getOrganizationUseCase(organizationId)
        }
    }
}