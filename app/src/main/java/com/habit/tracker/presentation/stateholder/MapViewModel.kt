package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Organization
import com.habit.tracker.domain.usecase.GetOrganizationsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel @Inject constructor(
    getOrganizationsUseCase: GetOrganizationsUseCase
) : BaseViewModel() {

    private val _organizations = MutableLiveData(listOf<Organization>())
    val organizations: LiveData<List<Organization>> = _organizations

    init {
        viewModelScope.launch {
            _organizations.value = getOrganizationsUseCase()
        }
    }
}