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

    init {
        viewModelScope.execute {
            _profile.postValue(getProfileUseCase())
        }
    }
}