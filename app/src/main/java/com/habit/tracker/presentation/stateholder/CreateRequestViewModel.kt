package com.habit.tracker.presentation.stateholder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.habit.tracker.core.BaseViewModel
import com.habit.tracker.domain.usecase.CreateRequestUseCase
import com.habit.tracker.presentation.model.RequestDraft
import javax.inject.Inject

class CreateRequestViewModel @Inject constructor(
    private val createRequestUseCase: CreateRequestUseCase
) : BaseViewModel() {

    private val _requestDraft = MutableLiveData(RequestDraft.empty())
    val requestDraft: LiveData<RequestDraft> = _requestDraft

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> = _isError

    private val _isSuccess = MutableLiveData(false)
    val isSuccess: LiveData<Boolean> = _isSuccess

    fun setTitle(value: String) {
        _requestDraft.value = requestDraft.value!!.copy(name = value)
    }

    fun setDescription(value: String) {
        _requestDraft.value = requestDraft.value!!.copy(description = value)
    }

    fun addPhoto(path: String) {
        val oldDraft = requestDraft.value!!
        val photos = oldDraft.photos.toMutableList()
        if (photos.size == 4) {
            photos[0] = path
        } else {
            photos.add(path)
        }
        _requestDraft.value = oldDraft.copy(photos = photos)
    }

    fun createRequest(organizationId: Int) {
        val requestDraft = requestDraft.value!!
        viewModelScope.execute(
            onError = {
                _isError.value = true
            },
            onSuccess = {
                _isSuccess.value = true
            }
        ) {
            createRequestUseCase(
                organizationId,
                requestDraft.name,
                requestDraft.description,
                requestDraft.photos
            )
        }
    }
}