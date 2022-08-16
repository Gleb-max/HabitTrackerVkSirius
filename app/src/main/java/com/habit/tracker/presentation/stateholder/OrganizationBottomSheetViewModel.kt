package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.entity.Request
import com.habit.tracker.domain.usecase.GetOrganizationUseCase
import com.habit.tracker.domain.usecase.GetRequestsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class OrganizationBottomSheetViewModel @Inject constructor(
    private val getOrganizationUseCase: GetOrganizationUseCase,
    private val getRequestsUseCase: GetRequestsUseCase
) : BaseViewModel() {

    private val _organization = MutableLiveData<Organization?>(null)
    val organization: LiveData<Organization?> = _organization

    private val _requests = MutableLiveData(listOf<Request>())
    val requests: LiveData<List<Request>> = _requests

    fun loadOrganizationData(organizationId: Int) {
        viewModelScope.launch {
            _requests.value = getRequestsUseCase(organizationId)
            _organization.value = getOrganizationUseCase(organizationId)
        }
    }
}