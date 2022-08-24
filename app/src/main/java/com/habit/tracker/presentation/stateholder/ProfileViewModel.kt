package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.entity.Profile
import com.habit.tracker.domain.usecase.GetProfileUseCase
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    getProfileUseCase: GetProfileUseCase
) : BaseViewModel() {

    private val _profile = MutableLiveData<Profile?>()
    val profile: LiveData<Profile?> = _profile

    private val _isErrorProfile = MutableLiveData<Boolean?>()
    var isErrorProfile: LiveData<Boolean?> = _isErrorProfile

    private val _shimmerCloseNeeded = MutableLiveData<Boolean?>()
    var shimmerCloseNeeded: LiveData<Boolean?> = _shimmerCloseNeeded


    init {
        viewModelScope.execute(onError = {
            _isErrorProfile.value = true
        },
        ) {
            _profile.postValue(getProfileUseCase())
            _shimmerCloseNeeded.postValue(true)
        }
    }
}